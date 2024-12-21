package com.myminorproject.digitalLibrary.controller;

import com.myminorproject.digitalLibrary.dto.AddBookRequest;
import com.myminorproject.digitalLibrary.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/addBook")
    public ResponseEntity<String> addBook(@RequestBody  AddBookRequest addBookRequest){
        bookService.createBook(addBookRequest.to());

        return ResponseEntity.status(HttpStatus.CREATED).body("Book Added Successfully!");

    }

    @GetMapping("/books")
    public List<String> findAllBooks(){
        return List.of("Kite Runner", "Alchemist", "The Splendid Sun");
    }

}
