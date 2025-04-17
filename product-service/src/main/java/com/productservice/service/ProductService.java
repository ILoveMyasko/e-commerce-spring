package com.productservice.service;

import com.productservice.dto.CreateProductRequestDto;
import com.productservice.dto.ProductDto;
import com.productservice.model.Product;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public interface ProductService {

    Optional<ProductDto> getProductById(Long id);

    ProductDto addNewProduct(CreateProductRequestDto createProductRequestDto);
}
