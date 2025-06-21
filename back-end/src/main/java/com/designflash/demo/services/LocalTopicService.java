package com.designflash.demo.services;

import com.designflash.demo.models.Topic;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocalTopicService {

    private List<Topic> localTopics = new ArrayList<>();

    public LocalTopicService() {
        loadTopicsFromFile();
    }

    private void loadTopicsFromFile() {
        try {
            ClassPathResource resource = new ClassPathResource("system-design-data.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));

            String line;
            String currentTitle = null;
            StringBuilder currentDescription = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (line.matches("^\\d+\\.\\s+.*")) { // Matches lines like "1. Title"
                    if (currentTitle != null) {
                        localTopics.add(new Topic(currentTitle, currentDescription.toString().trim()));
                        currentDescription.setLength(0);
                    }
                    currentTitle = line.replaceFirst("^\\d+\\.\\s+", "");
                } else if (!line.trim().isEmpty()) {
                    currentDescription.append(line).append("\n");
                }
            }

            if (currentTitle != null) {
                localTopics.add(new Topic(currentTitle, currentDescription.toString().trim()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Topic> getLocalTopics() {
        return localTopics;
    }
}
