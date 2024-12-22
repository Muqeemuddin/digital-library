package com.myminorproject.digitalLibrary.repository;

import com.myminorproject.digitalLibrary.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookDao extends JpaRepository<Book, Integer> {

    List<Book> findByTitle(String title);
    List<Book> findByGenre(String genre);
    //List<Book> findByAuthor(String authorName);

}
