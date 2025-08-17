package ru.ryb.budget.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ryb.budget.model.Category;
import ru.ryb.budget.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping(value = "v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category request) {
        return ResponseEntity.ok(categoryService.create(request));
    }

    @PutMapping
    public ResponseEntity<Category> update(@RequestBody Category request) {
        return ResponseEntity.ok(categoryService.update(request));
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
