package com.designflash.demo.controllers;

import com.designflash.demo.models.QAPair;
import com.designflash.demo.services.TextFileReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/questions")
public class QAController {

    private final TextFileReaderService qaService;

    public QAController(TextFileReaderService qaService) {
        this.qaService = qaService;
    }

    @GetMapping
    @CrossOrigin(origins = "*")
    public List<QAPair> getQuestions() throws IOException {
        return qaService.loadFromTxtFile();
    }

    @GetMapping("/random")
    @CrossOrigin(origins = "*")
    public QAPair getRandomQuestion() throws IOException {
        return qaService.getRandomQA();
    }
}
