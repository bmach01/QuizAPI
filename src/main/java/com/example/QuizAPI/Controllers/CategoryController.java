package com.example.QuizAPI.Controllers;


import com.example.QuizAPI.DatabaseMapping.Category;
import com.example.QuizAPI.DatabaseMapping.CategoryRepository;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Category> getByTitle(@PathVariable String title) {
        Category category = repository.findByTitle(title);
        if (category != null) return ResponseEntity.ok(category);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAll() {
        List<Category> categories = repository.findAll();
        if (!categories.isEmpty()) return ResponseEntity.ok(categories);
        return ResponseEntity.notFound().build();
    }

    public Category getById(String c_id) {
        return repository.findById(c_id).orElse(null);
    }
}
