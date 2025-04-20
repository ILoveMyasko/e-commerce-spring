package com.example.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

// Using Java Record for immutability and conciseness
public record AddItemRequestDto(

        @NotNull
        Long productId,

        @NotNull
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity

) {}