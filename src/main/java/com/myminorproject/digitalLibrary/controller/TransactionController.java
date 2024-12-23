package com.myminorproject.digitalLibrary.controller;

import com.myminorproject.digitalLibrary.dto.TransactionRequest;
import com.myminorproject.digitalLibrary.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/initiateTransaction")
    public ResponseEntity<String> borrowBook(@RequestBody @Valid TransactionRequest transactionRequest) throws Exception {
        return transactionService.updateTransaction(transactionRequest);


    }

}
