package com.myminorproject.digitalLibrary;

import com.myminorproject.digitalLibrary.models.Student;
import com.myminorproject.digitalLibrary.repository.StudentDao;
import com.myminorproject.digitalLibrary.services.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class StudentServiceTest {

    @InjectMocks
    StudentService studentService;

    @Mock
    StudentDao studentDao;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getStudentTest() throws Exception {
        String rollNo = "z1983925";
        Student expectedResult = Student.builder()
                .name("John")
                .age(23)
                .email("John@gmail.com")
                .rollNo(rollNo)
                .build();
        when(studentDao.findByRollNo(rollNo)).thenReturn(expectedResult);
        Student actualResult = studentService.getStudent("rollNo", rollNo);
        Assertions.assertEquals(expectedResult, actualResult);
    }
}
