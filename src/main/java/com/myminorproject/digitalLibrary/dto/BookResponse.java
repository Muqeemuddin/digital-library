package com.myminorproject.digitalLibrary.dto;


import com.myminorproject.digitalLibrary.models.Book;
import com.myminorproject.digitalLibrary.models.Genre;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Component
public class BookResponse {

    @NotBlank
    private Integer id;

    @NotBlank
    private String title;

    @NotBlank
    private Genre genre;

    @NotBlank
    private String authorName;

    private Double price;


    public List<BookResponse> from(List<Book> books){
       return  books.stream().map(book -> new BookResponse(book.getBookId(),
               book.getTitle(), book.getGenre(), book.getBook_author().getName(), book.getPrice())).collect(Collectors.toList());

    }

}
