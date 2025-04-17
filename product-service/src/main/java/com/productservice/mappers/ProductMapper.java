package com.productservice.mappers;

import com.productservice.dto.ProductDto;
import com.productservice.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDto convertToDto(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductDto(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getWeight(),
                product.getCategory());
    }
}

