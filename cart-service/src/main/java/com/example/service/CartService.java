package com.example.service;

import com.example.dto.AddItemRequestDto;
import com.example.dto.CartDto;
import com.example.model.Cart;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface CartService {

    CartDto getCartBySessionId(String sessionId);

    CartDto addItemBySession(String sessionId, AddItemRequestDto addItemRequestDto);

    CartDto deleteItemBySession(String sessionId, Long productId);

    Cart findOrCreateCartBySessionId(String sessionId);
}
