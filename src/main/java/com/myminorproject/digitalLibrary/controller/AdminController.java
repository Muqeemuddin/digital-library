package com.myminorproject.digitalLibrary.controller;

import com.myminorproject.digitalLibrary.dto.CreateAdminRequest;
import com.myminorproject.digitalLibrary.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/create")
    public ResponseEntity<String> createAdmin(@RequestBody @Valid CreateAdminRequest createAdminRequest){
        adminService.createAdmin(createAdminRequest.to());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Admin Created Successfully!");
    }
}
