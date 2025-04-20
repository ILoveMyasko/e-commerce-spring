package com.productservice.controller;

import com.productservice.dto.CategoryDto;
import com.productservice.dto.CreateCategoryRequestDto;
import com.productservice.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CreateCategoryRequestDto createCategoryRequestDto) {
        CategoryDto newCategoryDto = categoryService.addNewCategory(createCategoryRequestDto);
        URI location = ServletUriComponentsBuilder //very strange
                .fromCurrentContextPath()
                .path("/{id}") // /api/users/{id}
                .buildAndExpand(newCategoryDto.categoryId())
                .toUri();
        return ResponseEntity.created(location).body(newCategoryDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
}
