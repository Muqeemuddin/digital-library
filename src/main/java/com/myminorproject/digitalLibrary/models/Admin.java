package com.myminorproject.digitalLibrary.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

@Entity(name = "admins")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adminId;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private Date createdOn;

    //Admin has a relation with the transaction[1:n], One-to-Many relationship.
    @OneToMany(mappedBy = "transaction_admin")
    private List<Transaction> transactionList;

}
