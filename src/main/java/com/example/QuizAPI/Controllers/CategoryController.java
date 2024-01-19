package com.example.QuizAPI.Controllers;


import com.example.QuizAPI.DatabaseMapping.Category;
import com.example.QuizAPI.DatabaseMapping.CategoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepository repository;

    CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/title/{title}")
    public Category getByTitle(@PathVariable String title) {
        return repository.findByTitle(title);
    }

    @GetMapping("/all")
    public List<Category> getAll() {
        return repository.findAll();
    }

    public Category getById(String c_id) {
        return repository.findById(c_id).orElse(null);
    }
}
