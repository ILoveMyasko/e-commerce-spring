package com.example.controllers;

import com.example.dto.AddItemRequestDto;
import com.example.dto.CartDto;
import com.example.exceptions.ProductNotExistsException;
import com.example.service.CartService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {

    private static final String CART_SESSION_COOKIE_NAME = "cartSessionId";
    @Value("${app.cart.cookie.max-age-seconds}")
    private int COOKIE_MAX_AGE_SECONDS;
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    //TODO Fix first time after adding returns empty items
    @PostMapping("/items")
    public ResponseEntity<CartDto> addItemToCart(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @RequestBody AddItemRequestDto addItemRequestDto) {

        String sessionId = getOrCreateCartSessionId(request, response);
        CartDto updatedCart = cartService.addItemBySession(sessionId, addItemRequestDto);
        return ResponseEntity.ok(updatedCart);
    }
//return 1 if empty
    @GetMapping("/items")
    public ResponseEntity<CartDto> getCart(HttpServletRequest request, HttpServletResponse response) {
        String sessionId = getOrCreateCartSessionId(request, response);
        CartDto cartDto = cartService.getCartBySessionId(sessionId);
        return ResponseEntity.ok(cartDto);
    }

    private String getOrCreateCartSessionId(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String sessionId = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (CART_SESSION_COOKIE_NAME.equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }

        if (sessionId == null || sessionId.isBlank()) {
            sessionId = UUID.randomUUID().toString();
            Cookie newCookie = new Cookie(CART_SESSION_COOKIE_NAME, sessionId);
            newCookie.setPath("/");
            newCookie.setHttpOnly(true); // Protect from client-side script access
            newCookie.setMaxAge(COOKIE_MAX_AGE_SECONDS);
            // newCookie.setSecure(true); // IMPORTANT: Enable this if using HTTPS!
            newCookie.setAttribute("SameSite", "Lax"); //CSRF protection
            response.addCookie(newCookie);
        }
        return sessionId;
    }



}
