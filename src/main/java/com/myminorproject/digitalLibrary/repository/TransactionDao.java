package com.myminorproject.digitalLibrary.repository;

import com.myminorproject.digitalLibrary.models.Book;
import com.myminorproject.digitalLibrary.models.Student;
import com.myminorproject.digitalLibrary.models.Transaction;
import com.myminorproject.digitalLibrary.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionDao extends JpaRepository<Transaction, Integer> {

    Transaction findTransactionByStudentAndBookAndTransactionTypeOrderByTransIdDesc(Student student
            , Book book
            , TransactionType transactionType);



}
