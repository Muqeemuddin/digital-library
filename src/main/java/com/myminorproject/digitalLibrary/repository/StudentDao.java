package com.myminorproject.digitalLibrary.repository;

import com.myminorproject.digitalLibrary.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDao extends JpaRepository<Student,Integer> {
    Student findByRollNo(String rollNo);
}
