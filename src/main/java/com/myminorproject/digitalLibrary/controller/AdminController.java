package com.myminorproject.digitalLibrary.controller;

import com.myminorproject.digitalLibrary.dto.AdminResponse;
import com.myminorproject.digitalLibrary.dto.CreateAdminRequest;
import com.myminorproject.digitalLibrary.models.Admin;
import com.myminorproject.digitalLibrary.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminResponse adminResponse;

    @PostMapping("/create")
    public ResponseEntity<String> createAdmin(@RequestBody @Valid CreateAdminRequest createAdminRequest){
        adminService.createAdmin(createAdminRequest.to());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Admin Created Successfully!");
    }

    @GetMapping("/getAdmins")
    public List<AdminResponse> getAdmin(){
        List<Admin> admins = adminService.getAdmins();
        return adminResponse.from(admins);

    }
}
