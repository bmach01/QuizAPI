package com.example.QuizAPI.Controllers;

import com.example.QuizAPI.DatabaseMapping.Category;
import com.example.QuizAPI.DatabaseMapping.Question;
import com.example.QuizAPI.DatabaseMapping.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;


@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionRepository repository;
    private static final Random random = new Random();
    @Autowired
    CategoryController categoryController;

    QuestionController(QuestionRepository repository) {
        this.repository = repository;
    }

    public Question[] getRandomOf(int count, String c_id) {
        String[] ids = new String[count];
        Category category = categoryController.getById(c_id);

        if (category == null) return null;

        for (int i = 0; i < count; i++) {
            int randomized = random.nextInt(category.questions().size());
            ids[i] = category.questions().remove(randomized);
        }

        return repository.findAllWithIdIn(ids);
    }
}
