package com.myminorproject.digitalLibrary.services;

import com.myminorproject.digitalLibrary.models.Admin;
import com.myminorproject.digitalLibrary.repository.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    public void createAdmin(Admin admin){
        adminDao.save(admin);
    }
}
