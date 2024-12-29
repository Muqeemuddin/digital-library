package com.myminorproject.digitalLibrary.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "admins")
@Builder  //(builderClassName = "Builder", toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Admin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adminId;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @CreationTimestamp
    private Date createdOn;

    //Admin has a relation with the transaction[1:n], One-to-Many relationship.
    @OneToMany(mappedBy = "admin")
    private List<Transaction> transactionList;

}
