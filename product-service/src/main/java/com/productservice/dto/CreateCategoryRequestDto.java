package com.productservice.dto;

import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link com.productservice.model.Category}
 */
public record CreateCategoryRequestDto(
        @Size(min = 2, max = 50)
        String name) implements Serializable {
}