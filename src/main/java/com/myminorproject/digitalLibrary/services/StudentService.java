package com.myminorproject.digitalLibrary.services;

import com.myminorproject.digitalLibrary.models.Student;
import com.myminorproject.digitalLibrary.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentDao studentDao;

    public void createOrUpdateStudent(Student student){
        studentDao.save(student);
    }

    @Cacheable(value = "Student", key = "#searchValue")
    public Student getStudent(String searchKey, String searchValue) throws Exception{
        if (searchKey.equals("rollNo")) {
            return studentDao.findByRollNo(searchValue);
        }
        throw new Exception("Invalid search Key " + searchKey);
    }
}
