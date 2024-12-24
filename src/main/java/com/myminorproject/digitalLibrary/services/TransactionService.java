package com.myminorproject.digitalLibrary.services;

import com.myminorproject.digitalLibrary.dto.TransactionRequest;
import com.myminorproject.digitalLibrary.models.*;
import com.myminorproject.digitalLibrary.repository.AdminDao;
import com.myminorproject.digitalLibrary.repository.BookDao;
import com.myminorproject.digitalLibrary.repository.StudentDao;
import com.myminorproject.digitalLibrary.repository.TransactionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {

    @Value("${student.allowed.max-books}")
    Integer maxBooksAllowed;

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
                    .transaction_student(student)
                    .transaction_book(book)
                    .transaction_admin(admin)
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
     * return a book (Issuance) -> {studentRollNumber, bookId, adminId}
     *      1. Validate the request => student, book and admin is valid or not
     *      2. Validate if book is available or not => If book is already issue on someone else's name
     *      3. Validate if the book can be issued to a person or not => We need to check if student has issue limit
     *          available in his account
     *      4. Entry in transaction table => pending status
     *      5. Assign book to a student => Update student column in book table
     *      6. Update the status
     **/


    private String returnBook(TransactionRequest transactionRequest){
        return null;
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
