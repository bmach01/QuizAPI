package com.example.QuizAPI.DAL;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Questions")
public record Question(
        @Id String id,
        String text,
        String[] options,
        int correct
) {}
