package com.myminorproject.digitalLibrary.services;

import com.myminorproject.digitalLibrary.dto.TransactionRequest;
import com.myminorproject.digitalLibrary.models.*;
import com.myminorproject.digitalLibrary.repository.AdminDao;
import com.myminorproject.digitalLibrary.repository.BookDao;
import com.myminorproject.digitalLibrary.repository.StudentDao;
import com.myminorproject.digitalLibrary.repository.TransactionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private Transaction transaction;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private BookDao bookDao;

    public ResponseEntity<String> updateTransaction(TransactionRequest transactionRequest){
        String bookId = transactionRequest.getBookId();
        String adminId = transactionRequest.getAdminId();
        Optional<Book> book = bookDao.findById(Integer.valueOf(bookId));
        Optional<Admin> admin = adminDao.findById(Integer.valueOf(adminId));
        Transaction transaction = Transaction.builder().transaction_student(studentDao.findByRollNo(transactionRequest.getRollNo()))
                .transaction_book(book.get())
                .transaction_admin(admin.get())
                .transactionType(transactionRequest.getTransactionType()).build();
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transactionDao.save(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body("Transaction Complete.");
    }
}
