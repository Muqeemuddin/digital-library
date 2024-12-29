package com.myminorproject.digitalLibrary.services;

import com.myminorproject.digitalLibrary.models.Admin;
import com.myminorproject.digitalLibrary.repository.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    public void createAdmin(Admin admin){
        adminDao.save(admin);
    }

    public List<Admin> getAdmins(){
        return adminDao.findAll();
    }

    public Admin getAdmin(String adminId){
        Optional<Admin> admin = adminDao.findById(Integer.valueOf(adminId));
        return admin.orElseGet(Admin::new);

    }
}
