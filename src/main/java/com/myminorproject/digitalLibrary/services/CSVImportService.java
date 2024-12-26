package com.myminorproject.digitalLibrary.services;

import com.myminorproject.digitalLibrary.models.Student;
import com.myminorproject.digitalLibrary.repository.StudentDao;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.csv.CSVFormat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVImportService {

    @Autowired
    private StudentDao studentDao;

    public Integer processCSVAndSaveStudent(MultipartFile file) throws Exception {

        try{
            List<Student> studentsList = parseCSVToStudents(file.getInputStream());
            studentDao.saveAll(studentsList);
            return studentsList.size();
        } catch (Exception e) {
            throw new Exception("Unable to parse the file");
        }


    }

    private List<Student> parseCSVToStudents(InputStream inputStream) {
        try(BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8)
        )){
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader("Name", "Age", "Email", "rollNo")
                    .build();

            CSVParser csvParser = csvFormat.parse(bufferedReader);
            List<CSVRecord> csvRecords = csvParser.getRecords();
            csvRecords.removeFirst(); // First record holds the header of the file.

            List<Student> students = new ArrayList<>();
            for(CSVRecord csvRecord:csvRecords){
                Student student = Student.builder()
                        .name(csvRecord.get("Name"))
                        .age(Integer.valueOf(csvRecord.get("Age")))
                        .email(csvRecord.get("Email"))
                        .rollNo(csvRecord.get("rollNo"))
                        .build();

                students.add(student);
            }
            return students;
        }catch (Exception exception){
            throw new RuntimeException(exception);
        }
    }
}
