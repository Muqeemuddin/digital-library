package com.myminorproject.digitalLibrary.services;

import com.myminorproject.digitalLibrary.dto.BookResponse;
import com.myminorproject.digitalLibrary.models.Author;
import com.myminorproject.digitalLibrary.models.Book;
import com.myminorproject.digitalLibrary.models.Genre;
import com.myminorproject.digitalLibrary.repository.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private AuthorService authorService;

    public void createOrUpdateBook(Book book){

        Author author = authorService.getOrCreate(book.getBook_author());

        // Set the author
        book.setBook_author(author);


        // Save the book
        bookDao.save(book);
    }

    public List<Book> getBooks(){

        return bookDao.findAll();

    }

    public List<Book> getBooks(String searchKey, String searchValue) throws Exception {
        switch(searchKey){
            // Find by ID
            case "id":
                Optional<Book> bookById = bookDao.findById(Integer.parseInt(searchValue));
                /* if(bookById.isPresent()){
                    return List.of(bookById.get());
                }else{
                    return new ArrayList<>();
                } */

                // The Above logic is recreated using functional programming in the below expression.
                return bookById.map(List::of).orElseGet(ArrayList::new);


            case "title":
                return bookDao.findByTitle(searchValue);

//            case "authorName":
//                return bookDao.findByAuthor(searchValue);

            case "genre":
                return bookDao.findByGenre(Genre.valueOf(searchValue));

            default:
                throw new Exception("Invalid search Key "+ searchKey);

        }
    }

    public boolean deleteBook(String deleteKey, String deleteValue) throws Exception{
        switch (deleteKey){
            case "title":
                List<Book> booksByTitle = bookDao.findByTitle(deleteValue);
                for(Book book:booksByTitle){
                    bookDao.delete(book);
                }
                return true;

            case "bookId":
                bookDao.deleteById(Integer.valueOf(deleteValue));
                return true;


            default:
                throw new Exception("Invalid key " + deleteKey);
        }
    }

}
