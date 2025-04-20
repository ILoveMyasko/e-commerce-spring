package com.example.dto;

import java.io.Serializable;
import java.util.List;

public record CartDto(
        String sessionId, // TODO hide? from client if preferred
        List<CartItemDto> items
        //BigDecimal totalPrice // Calculated field
) {}
