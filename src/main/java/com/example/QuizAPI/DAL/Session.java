package com.example.QuizAPI.DAL;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("Sessions")
public record Session(
        @Id String id,
        String key,
        Date creation_date,
        String category,
        @DBRef Question[] questions
) {}
