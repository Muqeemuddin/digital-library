package com.myminorproject.digitalLibrary.repository;

import com.myminorproject.digitalLibrary.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminDao extends JpaRepository<Admin,Integer> {
}
