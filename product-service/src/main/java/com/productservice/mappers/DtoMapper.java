package com.productservice.mappers;

import com.productservice.dto.CategoryDto;
import com.productservice.dto.ProductDto;
import com.productservice.model.Category;
import com.productservice.model.Product;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {
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
                product.getCategory().getCategoryId(),
                product.getCategory().getName());
    }

    public CategoryDto convertToDto(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryDto(
                category.getCategoryId(),
                category.getName());
    }

}

