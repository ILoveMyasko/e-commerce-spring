package com.productservice.service;

import com.productservice.dto.CategoryDto;
import com.productservice.dto.CreateCategoryRequestDto;
import com.productservice.dto.CreateProductRequestDto;
import com.productservice.dto.ProductDto;
import com.productservice.model.Category;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CategoryService {
    boolean existsById(Long id);


    CategoryDto addNewCategory(CreateCategoryRequestDto createCategoryRequestDto);

    Optional<CategoryDto> getCategoryById(Long id);

    Optional<Category> getCategoryEntityById(Long id);
 }
