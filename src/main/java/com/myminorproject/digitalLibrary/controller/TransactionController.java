package com.myminorproject.digitalLibrary.controller;

import com.myminorproject.digitalLibrary.dto.TransactionRequest;
import com.myminorproject.digitalLibrary.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/initiateTransaction")
    public ResponseEntity<String> initiateTranx(@RequestBody @Valid TransactionRequest transactionRequest) throws Exception {
       try{
           String transactionId = transactionService.initiateTranx(transactionRequest);
           return ResponseEntity.ok("transactionId : " + transactionId);
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
       }


    }

}
