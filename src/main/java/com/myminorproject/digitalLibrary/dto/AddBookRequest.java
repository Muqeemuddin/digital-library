package com.myminorproject.digitalLibrary.dto;

import com.myminorproject.digitalLibrary.models.Author;
import com.myminorproject.digitalLibrary.models.Book;
import com.myminorproject.digitalLibrary.models.Genre;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddBookRequest {

    @NotBlank
    private String title;

    @NotBlank
    private Genre genre;

    @NotBlank
    private String authorName;

    @NotBlank
    private String authorEmail;

    private Double price;

    public Book to(){
        return Book.builder()
                .title(this.title)
                .genre(this.genre)
                .book_author(Author.builder().name(this.authorName).email(this.authorEmail).build())
                .price(this.price)
                .build();
    }

}
