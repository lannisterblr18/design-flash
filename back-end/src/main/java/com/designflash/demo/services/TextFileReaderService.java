package com.designflash.demo.services;

import com.designflash.demo.models.QAPair;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class TextFileReaderService {

    public List<QAPair> loadFromTxtFile() throws IOException {
        List<QAPair> result = new ArrayList<>();

        // Load file from resources folder
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("system-design-questions.txt");
        if (inputStream == null) {
            throw new IOException("File 'system-design-questions.txt' not found in resources folder.");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            String currentQuestion = null;
            StringBuilder answerBuilder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
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
        }

        return result;
    }
}
