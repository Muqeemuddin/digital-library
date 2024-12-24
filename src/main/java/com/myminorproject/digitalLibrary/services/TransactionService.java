package com.myminorproject.digitalLibrary.services;

import com.myminorproject.digitalLibrary.dto.TransactionRequest;
import com.myminorproject.digitalLibrary.models.*;
import com.myminorproject.digitalLibrary.repository.TransactionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Value("${student.allowed.max-books}")
    Integer maxBooksAllowed;

    @Value("${student.allowed.a-books-max-days}")
    Integer maxDaysABookAllowed;

    @Value("${fine.par.day}")
    double fineParDay;
    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private Transaction transaction;

    @Autowired
    private StudentService studentService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private BookService bookService;

    public String initiateTranx(TransactionRequest transactionRequest) throws Exception {

        return transactionRequest.getTransactionType() == TransactionType.BORROW
                ? borrowBook(transactionRequest)
                : returnBook(transactionRequest);

//        String bookId = transactionRequest.getBookId();
//        String adminId = transactionRequest.getAdminId();
//        Optional<Book> book = bookDao.findById(Integer.valueOf(bookId));
//        Optional<Admin> admin = adminDao.findById(Integer.valueOf(adminId));
//        Transaction transaction = Transaction.builder().transaction_student(studentDao.findByRollNo(transactionRequest.getRollNo()))
//                .transaction_book(book.get())
//                .transaction_admin(admin.get())
//                .transactionType(transactionRequest.getTransactionType()).build();
//        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
//        transactionDao.save(transaction);
//        return ResponseEntity.status(HttpStatus.CREATED).body("Transaction Complete.");
    }

    /**
     * borrow a book (Issuance) -> {studentRollNumber, bookId, adminId}
     *      1. Validate the request => student, book and admin is valid or not
     *      2. Validate if book is available or not => If book is already issue on someone else's name
     *      3. Validate if the book can be issued to a person or not => We need to check if student has issue limit
     *          available in his account
     *      4. Entry in transaction table => pending status
     *      5. Assign book to a student => Update student column in book table
     *      6. Update the status
     **/

    private String borrowBook(TransactionRequest transactionRequest) throws Exception {

//  1. Validate the request => student, book and admin is valid or not
        Student student = validateStudent(transactionRequest.getRollNo());
        if(student == null){
            throw new Exception("Invalid Student roll Number "+transactionRequest.getRollNo());
        }

        Admin admin = validateAdmin(transactionRequest.getAdminId());
        if(admin == null){
            throw new Exception("Invalid Admin " + transactionRequest.getAdminId());
        }

        Book book = validateBook(transactionRequest.getBookId());
        if(book == null){
            throw new Exception("Invalid Book Id " + transactionRequest.getBookId());
        }
//  2. Validate if book is available or not => If book is already issue on someone else's name
        if(book.getStudent() != null){
            throw new Exception("Book is not available. Book issued to "+book.getStudent().getName());
        }

//  3. Validate if the book can be issued to a person or not => We need to check if student has issue limit
//     available in his account.
        if(student.getBookList().size()>=maxBooksAllowed){
            throw new Exception("Student has exhausted the borrow limit.");
        }

        Transaction transaction = null;

        try{
            transaction = Transaction.builder()
                    .transactionID(UUID.randomUUID().toString())
                    .student(student)
                    .book(book)
                    .admin(admin)
                    .transactionType(TransactionType.BORROW)
                    .transactionStatus(TransactionStatus.PENDING)
                    .build();

            transactionDao.save(transaction);

//      4. Assign student to the Book
            book.setStudent(student);
            bookService.createOrUpdateBook(book);

//      5. Update the student's book_list to include this book.
            student.getBookList().add(book);
            studentService.createOrUpdateStudent(student);

//      6. Update the transaction status.
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);

        }catch (Exception exception){
            assert transaction != null;
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
        }finally{
            assert transaction != null;
            transactionDao.save(transaction);
        }

      return transaction.getTransactionID();
    }


    /**
     * return a book -> {studentRollNumber, bookId, adminId}
     *      1. Validate the request => student, book and admin is valid or not
     *      2. Get the corresponding BORROW transaction
     *      3. save the transaction in the table with pending status
     *      4. Unassign the book from the user and user from the book
     *      5. Update the transaction status to success
     *      6. Finally, save the transaction in the table
     **/


    private String returnBook(TransactionRequest transactionRequest) throws Exception{

//  1. Validate the request => student, book and admin is valid or not
        Student student = validateStudent(transactionRequest.getRollNo());
        if(student == null){
            throw new Exception("Invalid Student roll Number "+transactionRequest.getRollNo());
        }

        Admin admin = validateAdmin(transactionRequest.getAdminId());
        if(admin == null){
            throw new Exception("Invalid Admin " + transactionRequest.getAdminId());
        }

        Book book = validateBook(transactionRequest.getBookId());
        if(book == null){
            throw new Exception("Invalid Book Id " + transactionRequest.getBookId());
        }

//  1.1 Validate if this book is assigned to the student
        if(book.getStudent() == null){
            throw new Exception("Book was not assigned to student :" + student.getName());
        }

//  2. Get the corresponding issue transaction

        Transaction borrowedTransaction = transactionDao.findTransactionByStudentAndBookAndTransactionTypeOrderByTransIdDesc(
                student, book, TransactionType.BORROW
        );

        Transaction transaction = null;

        try{
            double fine = calculateFine(borrowedTransaction.getCreatedOn());
            transaction = Transaction.builder()
                    .transactionID(UUID.randomUUID().toString())
                    .student(student)
                    .book(book)
                    .admin(admin)
                    .transactionType(TransactionType.RETURN)
                    .transactionStatus(TransactionStatus.PENDING)
                    .fine(fine)
                    .build();

//      3. Save the transaction in the table
            transactionDao.save(transaction);

//      4. Unassign the student from the book and remove the book from book_list in student.
            book.setStudent(null);
            bookService.createOrUpdateBook(book);

            student.getBookList().remove(book);
            studentService.createOrUpdateStudent(student);

//      5. Update the transaction status to success
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);

        } catch (Exception e) {
            assert transaction != null;
            transaction.setTransactionStatus(TransactionStatus.FAILURE);

        }finally {
            assert transaction !=null;
            transactionDao.save(transaction);
        }


        return transaction.getTransactionID();
        //return null;
    }

    private double calculateFine(Date borrowedOn) {
        long borrowedOnInMillis = borrowedOn.getTime();
        long currentTimeInMillis = System.currentTimeMillis();

        long diff = currentTimeInMillis - borrowedOnInMillis;
        long totalDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        if(totalDays>maxDaysABookAllowed){
            long overdueDays = totalDays - maxDaysABookAllowed;
            return (double)overdueDays*fineParDay;
        }
        return 0;
    }


    private Student validateStudent(String rollNo){
        try{
            return studentService.getStudent("rollNo", rollNo);
        }catch (Exception exception){
            return null;
        }

    }

    private Admin validateAdmin(String adminId){
        try{
            return adminService.getAdmin(adminId);
        } catch (Exception e) {
            return null;
        }
    }

    private Book validateBook(String bookId){
        try{
            return bookService.getBooks("id", bookId).getFirst();
        } catch (Exception e) {
            return null;
        }

    }
}
