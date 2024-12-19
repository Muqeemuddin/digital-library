package com.myminorproject.digitalLibrary.repository;

import com.myminorproject.digitalLibrary.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDao extends JpaRepository<Transaction, Integer> {
}
