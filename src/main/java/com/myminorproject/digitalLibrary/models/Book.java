package com.myminorproject.digitalLibrary.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity(name = "books")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;

    private String title;

    // It is a foreign value. meaning we are not providing or creating it,
    // instead it will be given from the author table.
    // We need to do mapping here.
    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Author book_author;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    // This field is important to identify if the book is available or not.
    // Null -> available, NotNull -> issued to someone.
    @ManyToOne
    @JoinColumn
    private Student student;


    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    private Double price;

    //Book has a relation with the transaction[1:n], One-to-Many relationship.
    @OneToMany(mappedBy = "book")
    private List<Transaction> transactionList;



}
