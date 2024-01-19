package com.example.QuizAPI.DAL;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("Categories")
public record Category(
        @Id String id,
        String title,
        String desc,
        List<String> questions
) {}
