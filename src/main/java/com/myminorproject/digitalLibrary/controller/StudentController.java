package com.myminorproject.digitalLibrary.controller;


import com.myminorproject.digitalLibrary.dto.AddStudentRequest;
import com.myminorproject.digitalLibrary.dto.StudentResponse;
import com.myminorproject.digitalLibrary.dto.StudentSearchRequest;
import com.myminorproject.digitalLibrary.models.Student;
import com.myminorproject.digitalLibrary.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentResponse studentResponse;

    @PostMapping("/createStudent")
    public ResponseEntity<String> createStudent(@RequestBody @Valid AddStudentRequest addStudentRequest){

        studentService.createOrUpdateStudent(addStudentRequest.to());
        return ResponseEntity.status(HttpStatus.CREATED).body("Student Added Successfully!");

    }

    @GetMapping("/getStudent")
    public StudentResponse getStudent(@RequestBody @Valid StudentSearchRequest studentSearchRequest) throws Exception{
         Student student = studentService.getStudent(studentSearchRequest.getSearchKey(),studentSearchRequest.getSearchValue());
         return studentResponse.from(student);
    }
}
