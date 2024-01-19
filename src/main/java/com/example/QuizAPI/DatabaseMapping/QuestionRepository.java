package com.example.QuizAPI.DatabaseMapping;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface QuestionRepository extends MongoRepository<Question, String> {
    @Query(value = "{ '_id' : {'$in' : ?0 } }")
    Question[] findAllWithIdIn(String[] ids);
}