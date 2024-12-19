package com.myminorproject.digitalLibrary.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity(name = "transactions")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transId;

    @ManyToOne
    @JoinColumn(name="transaction_student_id", referencedColumnName = "studentId")
    private Student transaction_student;

    @ManyToOne
    @JoinColumn(name="transaction_book_id", referencedColumnName = "bookId")
    private Book transaction_book;

    @ManyToOne
    @JoinColumn(name="transaction_admin_id", referencedColumnName = "adminId")
    private Admin transaction_admin;

    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;


    @CreationTimestamp
    private Date createdOn;
}
