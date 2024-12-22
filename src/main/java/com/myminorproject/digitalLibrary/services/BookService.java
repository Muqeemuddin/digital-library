package com.myminorproject.digitalLibrary.services;

import com.myminorproject.digitalLibrary.dto.BookResponse;
import com.myminorproject.digitalLibrary.models.Author;
import com.myminorproject.digitalLibrary.models.Book;
import com.myminorproject.digitalLibrary.repository.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private AuthorService authorService;

    public void createBook(Book book){

        Author author = authorService.getOrCreate(book.getBook_author());

        // Set the author
        book.setBook_author(author);

        // Save the book
        bookDao.save(book);
    }

    public List<Book> getBooks(){

        return bookDao.findAll();

    }

}
