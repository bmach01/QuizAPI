package com.example.QuizAPI.DAL;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
    Category findByTitle(String title);
}
