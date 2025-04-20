package com.example.dto;

public record CartItemDto(
        Long productId,
        int quantity
        // Add productName if needed later by fetching from Product Service during mapping
) {}