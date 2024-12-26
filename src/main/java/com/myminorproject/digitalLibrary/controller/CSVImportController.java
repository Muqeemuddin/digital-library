package com.myminorproject.digitalLibrary.controller;

import com.myminorproject.digitalLibrary.services.CSVImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/csv")
public class CSVImportController {

    @Autowired
    private CSVImportService csvImportService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCSV(@RequestParam("file")MultipartFile file, @RequestParam("entity") String key){
        try{
            Integer records = csvImportService.processCSVAndSaveStudent(file);
            return ResponseEntity.ok("File processed and saved" + records+ " RECORDS successfully!");
        }catch(Exception exception){
            return ResponseEntity.ok(exception.getMessage());
        }
    }

}
