package com.example.QuizAPI.Controller;


import com.example.QuizAPI.DAL.Category;
import com.example.QuizAPI.DAL.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/title/{title}")
    public Category byTitle(@PathVariable String title) {
        return repository.findByTitle(title);
    }

    @GetMapping("/all")
    List<Category> all() {
        return repository.findAll();
    }

    @GetMapping("/id/{c_id}")
    Category byId(@PathVariable String c_id) {
        return repository.findById(c_id).orElse(null);
    }
}
