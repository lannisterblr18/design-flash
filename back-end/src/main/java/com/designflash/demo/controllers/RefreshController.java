package com.designflash.demo.controllers;

import com.designflash.demo.services.LocalTopicService;
import com.designflash.demo.services.TextFileReaderService;
import org.apache.catalina.connector.Response;
import org.springframework.web.bind.annotation.*;

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
    public boolean refreshFiles() {
        Thread.ofVirtual().start(()-> {
            localTopicService.refreshTopics();
            textFileReaderService.refreshQuestions();
        });
        return true;
    }
}
