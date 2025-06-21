package com.designflash.demo.services;

import com.designflash.demo.models.QAPair;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Service
@Log4j2
public class TextFileReaderService {

    private final List<QAPair> qaPairs;
    private Random random;

    public TextFileReaderService() {
        qaPairs = loadFromTxtFile();
        random = new Random();
    }

    public List<QAPair> loadFromTxtFile()  {
        List<QAPair> result = new ArrayList<>();
        URL url = null;
        try {
            url = new URL("https://rkaoposxibtxbzdepifv.supabase.co/storage/v1/object/public/system-design//system-design-questions.txt");
        } catch (MalformedURLException e) {
            log.error("Exception while reading url for topics file {}", e.getMessage());
            throw new RuntimeException(e);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            String currentQuestion = null;
            StringBuilder answerBuilder = new StringBuilder();

            while (true) {
                try {
                    if (!((line = reader.readLine()) != null)) break;
                } catch (IOException e) {
                    log.error("Exception while reading line of the topics file {}", e.getMessage());
                    throw new RuntimeException(e);
                }
                if (line.startsWith("Q:")) {
                    if (currentQuestion != null) {
                        result.add(new QAPair(currentQuestion, answerBuilder.toString().trim()));
                        answerBuilder.setLength(0);
                    }
                    currentQuestion = line.substring(2).trim();
                } else {
                    answerBuilder.append(line).append("\n");
                }
            }

            if (currentQuestion != null) {
                result.add(new QAPair(currentQuestion, answerBuilder.toString().trim()));
            }
        } catch (IOException e) {
            log.error("Exception while reading lines of the topics file {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return result;
    }

    public QAPair getRandomQA() {
        int randomIndex = random.nextInt(0, qaPairs.size());
        return qaPairs.get(randomIndex);
    }

    public void refreshQuestions() {
        loadFromTxtFile();
    }
 }
