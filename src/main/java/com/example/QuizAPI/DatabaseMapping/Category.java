package com.example.QuizAPI.DatabaseMapping;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("Categories")
public record Category(
        @Id String id,
        String title,
        String desc,
        String iconLink,
        List<String> questions
) {}
