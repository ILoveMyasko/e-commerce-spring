package com.productservice.controller;

import com.productservice.dto.CreateProductRequestDto;
import com.productservice.dto.ProductDto;
import com.productservice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid CreateProductRequestDto createProductRequestDto) {

        ProductDto newProductDto = productService.addNewProduct(createProductRequestDto);
        URI location = ServletUriComponentsBuilder //very strange
                .fromCurrentContextPath()
                .path("/{id}") // /api/users/{id}
                .buildAndExpand(newProductDto.productId())
                .toUri();
        return ResponseEntity.created(location).body(newProductDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable long id) {
        return ResponseEntity.ok(productService.getProductById(id));

    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
}
