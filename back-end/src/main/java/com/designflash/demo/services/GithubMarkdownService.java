package com.designflash.demo.services;

import org.springframework.stereotype.Service;

import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.text.TextContentRenderer;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class GithubMarkdownService {
    private final String RAW_README_URL = "https://raw.githubusercontent.com/donnemartin/system-design-primer/master/README.md";
    private final RestTemplate restTemplate = new RestTemplate();
    private final Parser parser = Parser.builder().build();
    private final TextContentRenderer renderer = TextContentRenderer.builder().build();

    public List<Map<String, String>> getFlashcardsFromReadme() {
        String markdown = restTemplate.getForObject(RAW_README_URL, String.class);
        if (markdown == null) return Collections.emptyList();

        Node document = parser.parse(markdown);
        List<Map<String, String>> flashcards = new ArrayList<>();

        document.accept(new AbstractVisitor() {
            @Override
            public void visit(Heading heading) {
                if (heading.getLevel() == 2) {
                    String title = renderer.render(heading).trim();
                    StringBuilder description = new StringBuilder();

                    Node sibling = heading.getNext();
                    while (sibling != null && !(sibling instanceof Heading)) {
                        description.append(renderer.render(sibling)).append("\n");
                        sibling = sibling.getNext();
                    }

                    flashcards.add(Map.of(
                            "title", title,
                            "description", description.toString().trim()
                    ));
                }
            }
        });

        return flashcards;
    }

    public Map<String, String> getRandomFlashcard() {
        List<Map<String, String>> all = getFlashcardsFromReadme();
        if (all.isEmpty()) return Map.of("title", "No Data", "description", "Could not load from GitHub");
        return all.get(new Random().nextInt(all.size()));
    }
}
