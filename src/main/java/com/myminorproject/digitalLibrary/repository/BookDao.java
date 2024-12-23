package com.myminorproject.digitalLibrary.repository;

import com.myminorproject.digitalLibrary.models.Book;
import com.myminorproject.digitalLibrary.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDao extends JpaRepository<Book, Integer> {

    List<Book> findByTitle(String title);
    List<Book> findByGenre(Genre genre);
    //List<Book> findByAuthor(String authorName);

}
