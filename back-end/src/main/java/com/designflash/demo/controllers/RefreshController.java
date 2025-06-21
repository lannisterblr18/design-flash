package com.designflash.demo.controllers;

import com.designflash.demo.services.LocalTopicService;
import com.designflash.demo.services.TextFileReaderService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/refresh")
public class RefreshController {

    private final LocalTopicService localTopicService;
    private final TextFileReaderService textFileReaderService;


    public RefreshController(LocalTopicService localTopicService, TextFileReaderService textFileReaderService) {
        this.localTopicService = localTopicService;
        this.textFileReaderService = textFileReaderService;
    }

    @GetMapping
    @CrossOrigin(origins = "*")
    public void refreshFiles() {
        localTopicService.refreshTopics();
        textFileReaderService.refreshQuestions();
    }
}
