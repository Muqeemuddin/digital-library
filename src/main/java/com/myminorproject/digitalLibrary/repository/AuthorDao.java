package com.myminorproject.digitalLibrary.repository;

import com.myminorproject.digitalLibrary.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorDao extends JpaRepository<Author, Integer> {
    Author findByEmail(String email);
}
