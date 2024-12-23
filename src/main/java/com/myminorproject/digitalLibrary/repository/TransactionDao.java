package com.myminorproject.digitalLibrary.repository;

import com.myminorproject.digitalLibrary.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDao extends JpaRepository<Transaction, Integer> {
}
