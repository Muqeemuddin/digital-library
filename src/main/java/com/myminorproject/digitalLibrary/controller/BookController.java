package com.myminorproject.digitalLibrary.controller;

import com.myminorproject.digitalLibrary.dto.AddBookRequest;
import com.myminorproject.digitalLibrary.dto.BookResponse;
import com.myminorproject.digitalLibrary.models.Book;
import com.myminorproject.digitalLibrary.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookResponse bookResponse;

    @PostMapping("/addBook")
    public ResponseEntity<String> addBook(@RequestBody  AddBookRequest addBookRequest){
        bookService.createBook(addBookRequest.to());

        return ResponseEntity.status(HttpStatus.CREATED).body("Book Added Successfully!");

    }

    // API to fetch all the books

    @GetMapping("/getBooks")
    public List<BookResponse> findAllBooks(){
        try{
            List<Book> bookList =  bookService.getBooks();
            return bookResponse.from(bookList);
        }catch (Exception e){
            //
            return new ArrayList<>();
        }


    }

    // API to fetch books based on filter criteria.

//    @GetMapping("/getBooksOnFilter")
//    public List

}
