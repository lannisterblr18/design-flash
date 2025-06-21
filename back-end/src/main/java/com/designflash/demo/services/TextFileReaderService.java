package com.designflash.demo.services;

import com.designflash.demo.models.QAPair;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class TextFileReaderService {
    public List<QAPair> loadFromTxtFile() throws IOException {
        List<QAPair> result = new ArrayList<>();
        Path path = Paths.get("system-design-questions.txt");
        List<String> lines = Files.readAllLines(path);

        String currentQuestion = null;
        StringBuilder answerBuilder = new StringBuilder();

        for (String line : lines) {
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

        return result;
    }
}
