package com.designflash.demo.controllers;

import com.designflash.demo.models.Topic;
import com.designflash.demo.services.LocalTopicService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/topics/v2")
public class LocalTopicController {

    private final LocalTopicService localTopicService;
    private final Random random;

    public LocalTopicController(LocalTopicService localTopicService) {
        this.localTopicService = localTopicService;
        this.random = new Random();
    }

    @GetMapping
    public List<Topic> getAllLocalTopics() {
        return localTopicService.getLocalTopics();
    }

    @GetMapping("/random")
    @CrossOrigin(origins = "*")
    public Topic getRandomLocalTopic() {
        List<Topic> topics = localTopicService.getLocalTopics();
        if (topics.isEmpty()) return new Topic("No topics found", "Your local topic list is empty.");
        return topics.get(random.nextInt(topics.size()));
    }
}
