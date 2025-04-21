package com.productservice.service.implementation;

import com.productservice.dto.CategoryDto;
import com.productservice.dto.CreateCategoryRequestDto;
import com.productservice.exceptions.ResourceDuplicateException;
import com.productservice.exceptions.ResourceNotFoundException;
import com.productservice.mappers.DtoMapper;
import com.productservice.model.Category;
import com.productservice.repository.CategoryRepository;
import com.productservice.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final DtoMapper dtoMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, DtoMapper dtoMapper) {
        this.categoryRepository = categoryRepository;
        this.dtoMapper = dtoMapper;
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    @Transactional
    public CategoryDto addNewCategory(CreateCategoryRequestDto createCategoryRequestDto) {
        if (existsByName(createCategoryRequestDto.name()))
        {
            throw new ResourceDuplicateException("Category " + createCategoryRequestDto.name() + " already exists");
        }
        Category category = new Category();
        category.setName(createCategoryRequestDto.name());
        Category savedCategory = categoryRepository.save(category);
        return dtoMapper.convertToDto(savedCategory);
    }

    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(dtoMapper::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Category " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public Category getCategoryEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(dtoMapper::convertToDto)
                .toList();
    }


}
