package com.productservice.service.implementation;

import com.productservice.repository.CategoryRepository;
import com.productservice.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }


}
