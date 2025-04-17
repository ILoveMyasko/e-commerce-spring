package com.productservice.service.implementation;

import com.productservice.dto.CreateProductRequestDto;
import com.productservice.dto.ProductDto;
import com.productservice.mappers.ProductMapper;
import com.productservice.model.Product;
import com.productservice.repository.ProductRepository;
import com.productservice.service.CategoryService;
import com.productservice.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final  CategoryService categoryService;
    private final ProductMapper productMapper;
    public ProductServiceImpl(CategoryService categoryService, ProductRepository repository, ProductMapper productMapper) {
        this.repository = repository;
        this.categoryService = categoryService;
        this.productMapper = productMapper;
    }


    public Optional<ProductDto> getProductById(Long id) {
        return repository.findById(id)
                .map(productMapper::convertToDto);
    }

    //@Transactional
    public ProductDto addNewProduct(CreateProductRequestDto productRequestDto) {
        if(!categoryService.existsById(productRequestDto.category().getCategoryId()))
        {
            throw new IllegalArgumentException("Category not exists");
        }
        Product newProduct = new Product();
        newProduct.setName(productRequestDto.name());
        newProduct.setPrice(productRequestDto.price());
        newProduct.setDescription(productRequestDto.description());
        newProduct.setIsActive(productRequestDto.isActive());
        newProduct.setWeight(productRequestDto.weight());

        Product savedProduct = repository.save(newProduct);
        //kafka.
        return productMapper.convertToDto(savedProduct);
    }
}
