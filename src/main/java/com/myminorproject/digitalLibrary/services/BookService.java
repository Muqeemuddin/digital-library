package com.myminorproject.digitalLibrary.services;

import com.myminorproject.digitalLibrary.models.Author;
import com.myminorproject.digitalLibrary.models.Book;
import com.myminorproject.digitalLibrary.repository.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
