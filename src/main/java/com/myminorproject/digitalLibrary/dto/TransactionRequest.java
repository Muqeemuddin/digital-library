package com.myminorproject.digitalLibrary.dto;

import com.myminorproject.digitalLibrary.models.Book;
import com.myminorproject.digitalLibrary.models.Transaction;
import com.myminorproject.digitalLibrary.models.TransactionType;
import com.myminorproject.digitalLibrary.services.AdminService;
import com.myminorproject.digitalLibrary.services.BookService;
import com.myminorproject.digitalLibrary.services.StudentService;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class TransactionRequest {
    @NotNull
    private String rollNo;
    @NotNull
    private String bookId;
    @NotNull
    private String adminId;
    @NotNull
    private TransactionType transactionType;


}
