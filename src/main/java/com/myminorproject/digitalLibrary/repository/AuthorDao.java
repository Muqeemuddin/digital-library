package com.myminorproject.digitalLibrary.repository;

import com.myminorproject.digitalLibrary.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorDao extends JpaRepository<Author, Integer> {
}
