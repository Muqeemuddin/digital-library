package com.myminorproject.digitalLibrary.dto;

import com.myminorproject.digitalLibrary.models.Book;
import com.myminorproject.digitalLibrary.models.Student;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class StudentResponse {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String rollNo;

    @NotBlank
    private Integer age;

    private List<Book> bookList;

    public StudentResponse from(Student student){
        return  new StudentResponse(
                student.getName(), student.getEmail(), student.getRollNo(), student.getAge(), student.getBookList());
    }
}
