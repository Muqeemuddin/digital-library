package com.myminorproject.digitalLibrary.controller;

import com.myminorproject.digitalLibrary.dto.AddBookRequest;
import com.myminorproject.digitalLibrary.dto.BookResponse;
import com.myminorproject.digitalLibrary.dto.DeleteRequest;
import com.myminorproject.digitalLibrary.dto.SearchRequest;
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

    // API to fetch all the books or books based on some criteria.

    @GetMapping("/getBooks")
    public List<BookResponse> findAllBooks(@RequestBody SearchRequest searchRequest) throws Exception {

        if(searchRequest.getSearchKey()==null || searchRequest.getSearchValue() == null){
            try{
                List<Book> bookList =  bookService.getBooks();
                return bookResponse.from(bookList);
            }catch (Exception e){
                //
                return new ArrayList<>();
            }
        }else{
            List<Book> bookList = bookService.getBooks(searchRequest.getSearchKey(), searchRequest.getSearchValue());
            return bookResponse.from(bookList);

        }

    }

    @DeleteMapping("/deleteBook")
    public ResponseEntity<String> deleteBook(@RequestBody @Valid DeleteRequest deleteRequest) throws Exception{
        if(bookService.deleteBook(deleteRequest.getDeleteKey(), deleteRequest.getDeleteValue())){
            return ResponseEntity.status(HttpStatus.OK).body("Book(s) Deleted Successfully!");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Book(s) Deleted!");
        }
    }


}
