package com.myminorproject.digitalLibrary.dto;

import com.myminorproject.digitalLibrary.models.Student;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class AddStudentRequest {
    @NotNull
    private String name;
    @NotNull
    private String email;

    private String rollNo;

    private String age;

    public Student to(){
        return Student.builder().name(this.name).email(this.email)
                .rollNo(this.rollNo).age(Integer.parseInt(this.age)).build();
    }
}
