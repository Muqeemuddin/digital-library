package com.myminorproject.digitalLibrary.repository;

import com.myminorproject.digitalLibrary.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDao extends JpaRepository<Book, Integer> {
}
