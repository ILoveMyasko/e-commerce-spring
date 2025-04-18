package com.productservice.dto;

import java.io.Serializable;

/**
 * DTO for {@link com.productservice.model.Category}
 */
public record CategoryDto(Long categoryId,
                          String name) implements Serializable {
  }