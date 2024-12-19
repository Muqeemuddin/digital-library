package com.myminorproject.digitalLibrary.models;

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
    private Author book_author;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;


    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    //Book has a relation with the transaction[1:n], One-to-Many relationship.
    @OneToMany(mappedBy = "transaction_book")
    private List<Transaction> transactionList;



}
