package com.example.mappers;


import com.example.dto.CartDto;
import com.example.dto.CartItemDto;
import com.example.model.Cart;
import com.example.model.CartItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartDto convertToDto(Cart cart) {
        if (cart == null) {
            return null;
        }

//        BigDecimal totalPrice = cart.getItems().stream()
//                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartDto(
                cart.getSessionId(),
                cart.getItems() == null ? Collections.emptyList() : cart.getItems().stream()
                        .map(this::convertItemToDto)
                        .collect(Collectors.toList())
        );
    }

    public CartItemDto convertItemToDto(CartItem item) {
        if (item == null) {
            return null;
        }
        return new CartItemDto(
                item.getProductId(),
                item.getQuantity()
        );
    }
}