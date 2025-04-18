package com.productservice.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.productservice.model.Product}
 */
public record ProductDto(Long productId,
                         String name,
                         String description,
                         BigDecimal price,
                         Integer weight,
                         Long categoryId,
                         String categoryName
) implements Serializable {
}