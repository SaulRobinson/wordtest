package com.synalogik.wordtest.controller;

import com.synalogik.wordtest.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/file")
public class WordController {

    @Autowired
    private WordService wordService;

    // Endpoint to collect file to analyze
    @RequestMapping(path = "/analyzeFile", method = RequestMethod.POST
            , consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> analyzeFile(
            @RequestPart(value = "file") MultipartFile file
    ){
        return new ResponseEntity<>(wordService.analyzeFileContents(file), HttpStatus.OK);
    }
}
