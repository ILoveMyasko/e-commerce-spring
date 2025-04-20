package com.productservice.service.implementation;

import com.productservice.dto.CreateProductRequestDto;
import com.productservice.dto.ProductDto;
import com.productservice.exceptions.ResourceNotFoundException;
import com.productservice.mappers.DtoMapper;
import com.productservice.model.Category;
import com.productservice.model.Product;
import com.productservice.repository.ProductRepository;
import com.productservice.service.CategoryService;
import com.productservice.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final  CategoryService categoryService;
    private final DtoMapper dtoMapper;
    public ProductServiceImpl(CategoryService categoryService, ProductRepository repository, DtoMapper dtoMapper) {
        this.repository = repository;
        this.categoryService = categoryService;
        this.dtoMapper = dtoMapper;
    }


    public ProductDto getProductById(Long id) {
        return repository.findById(id)
                .map(dtoMapper::convertToDto)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found with id:" + id));
    }

    @Transactional
    public ProductDto addNewProduct(CreateProductRequestDto productRequestDto) {

        Category category = categoryService.getCategoryEntityById(productRequestDto.categoryId());//throws if not found
        Product newProduct = new Product();
        newProduct.setName(productRequestDto.name());
        newProduct.setPrice(productRequestDto.price());
        newProduct.setDescription(productRequestDto.description());
        newProduct.setIsActive(productRequestDto.isActive());
        newProduct.setWeight(productRequestDto.weight());
        newProduct.setCategory(category);
        Product savedProduct = repository.save(newProduct);
        //kafka.

        return dtoMapper.convertToDto(savedProduct);
    }
}
