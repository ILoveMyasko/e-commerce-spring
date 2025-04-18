package com.productservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.productservice.model.Product}
 */
public record CreateProductRequestDto(
        @Size(min = 2, max = 50)
        String name,
        @NotEmpty
        String description,
        @NotNull @Positive
        BigDecimal price,
        @NotNull
        Boolean isActive,
        @Positive @NotNull
        Integer weight,
        @NotNull
        Long categoryId) implements Serializable {
}