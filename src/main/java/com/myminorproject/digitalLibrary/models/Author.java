package com.myminorproject.digitalLibrary.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity(name = "authors")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authorId;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private Date createdOn;

    //Author has a relation with the Book[1:n], One-to-Many relationship.
    @OneToMany(mappedBy = "book_author")
    private List<Book> bookList;
}
