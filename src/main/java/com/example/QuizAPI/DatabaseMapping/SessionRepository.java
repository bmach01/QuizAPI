package com.example.QuizAPI.DatabaseMapping;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionRepository extends MongoRepository<Session, String> {
    Session findFirstByOrderByKeyDesc();
    Session findByKey(String key);

}