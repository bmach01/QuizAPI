package com.example.QuizAPI.Controller;

import com.example.QuizAPI.DAL.Category;
import com.example.QuizAPI.DAL.Question;
import com.example.QuizAPI.DAL.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    private static final Random random = new Random();
    @Autowired
    CategoryController categoryController;


    QuestionController(QuestionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    List<Question> getAll() {
        return repository.findAll();
    }

    @GetMapping("/id/{q_id}")
    public ResponseEntity<Question> getById(@PathVariable String q_id) {
        Question question = repository.findById(q_id).orElse(null);
        if (question != null) return ResponseEntity.ok(question);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/random/{count}/of/{c_id}")
    Question[] getRandomOf(@PathVariable int count, @PathVariable String c_id) {
        String[] ids = new String[count];
        Category category = categoryController.byId(c_id);

        if (category == null) return null;

        for (int i = 0; i < count; i++) {
            int randomized = random.nextInt(category.questions().size());
            ids[i] = category.questions().remove(randomized);
        }

        return repository.findAllWithIdIn(ids);
    }
}
