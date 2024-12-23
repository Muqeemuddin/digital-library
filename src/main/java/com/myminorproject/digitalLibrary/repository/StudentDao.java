package com.myminorproject.digitalLibrary.repository;

import com.myminorproject.digitalLibrary.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDao extends JpaRepository<Student,Integer> {
    Student findByRollNo(String rollNo);
}
