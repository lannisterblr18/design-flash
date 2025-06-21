package com.designflash.demo.controllers;

import com.designflash.demo.services.GithubMarkdownService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/topics/v1")
public class TopicController {

    private final GithubMarkdownService githubService;

    public TopicController(GithubMarkdownService githubService) {
        this.githubService = githubService;
    }

    @GetMapping
    public List<Map<String, String>> getAllTopics() {
        return githubService.getFlashcardsFromReadme();
    }

    @GetMapping("/random")
    @CrossOrigin(origins = "*")
    public Map<String, String> getRandomTopic() {
        return githubService.getRandomFlashcard();
    }

}
