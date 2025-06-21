package com.designflash.demo.services;

import com.designflash.demo.models.Topic;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class LocalTopicService {

    private List<Topic> localTopics = new ArrayList<>();

    public LocalTopicService() {
        loadTopicsFromFile();
    }

    private void loadTopicsFromFile() {
        try {
            URL url = null;
            try {
                url = new URL("https://rkaoposxibtxbzdepifv.supabase.co/storage/v1/object/public/system-design//system-design-data.txt");
            } catch (MalformedURLException e) {
                log.error("Exception while reading url for topics file {}", e.getMessage());
                throw new RuntimeException(e);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));

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
            log.error("Exception while reading lines of the questions file: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public void refreshTopics() {
        loadTopicsFromFile();
    }

    public List<Topic> getLocalTopics() {
        return localTopics;
    }
}
