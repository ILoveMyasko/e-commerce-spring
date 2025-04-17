package com.productservice.dto;

import com.productservice.model.Category;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.productservice.model.Product}
 */
public record CreateProductRequestDto(String name,
                                      String description,
                                      BigDecimal price,
                                      Boolean isActive,
                                      Integer weight,
                                      Category category) implements Serializable {
}