package com.myminorproject.digitalLibrary.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity(name = "students")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentId;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String rollNo;

    private Integer age;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    //Student has a relation with the transaction[1:n], One-to-Many relationship.
    @OneToMany(mappedBy = "transaction_student")
    private List<Transaction> transactionList;
}
