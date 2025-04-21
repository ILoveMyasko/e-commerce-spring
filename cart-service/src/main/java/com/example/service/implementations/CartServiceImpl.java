package com.example.service.implementations;

import com.example.dto.AddItemRequestDto;
import com.example.dto.CartDto;
import com.example.exceptions.ProductNotExistsException;
import com.example.mappers.CartMapper;
import com.example.model.Cart;
import com.example.model.CartItem;
import com.example.repository.CartItemRepository;
import com.example.repository.CartRepository;
import com.example.service.CartService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    private final RestTemplate restTemplate;
    @Value("${product-service.base-url}")
    private String productServiceBaseUrl;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository,
                           CartMapper cartMapper, RestTemplate restTemplate) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartMapper = cartMapper;
        this.restTemplate = restTemplate;
    }


    public CartDto getCartBySessionId(String sessionId) {
        return null;
    }

    @Transactional
    public CartDto addItemBySession(String sessionId, AddItemRequestDto addItemRequestDto) {

        try {
            restTemplate.getForEntity(productServiceBaseUrl + "/products/" + addItemRequestDto.productId(),
                    String.class);
        } catch (Exception ex) {
            if (ex.getClass() == HttpClientErrorException.NotFound.class) {
                throw new ProductNotExistsException(ex.getMessage());
            } else {
                throw new ProductNotExistsException("Error communicating with Product service: " + ex.getMessage());
            }
        }
        //if cart not exists we create new
        Cart sessionCart = findOrCreateCartBySessionId(sessionId);
        Optional<CartItem> existingItemOpt = cartItemRepository.
                findByCartAndProductId(sessionCart, addItemRequestDto.productId());
        //if an item exists we just increment quantity, race cond safe?
        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + addItemRequestDto.quantity());
            cartItemRepository.save(existingItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(sessionCart);
            cartItem.setProductId(addItemRequestDto.productId());
            cartItem.setQuantity(addItemRequestDto.quantity());
            cartItem.setPrice(addItemRequestDto.price());
            sessionCart.addItem(cartItem);// double add?
            cartItemRepository.save(cartItem);
        }

        Cart updatedCart = cartRepository.findById(sessionCart.getId()).orElse(sessionCart);
        System.out.println(updatedCart.getItems());
        return cartMapper.convertToDto(updatedCart);
    }

    private Cart findOrCreateCartBySessionId(String sessionId) {
        return cartRepository.findBySessionId(sessionId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setSessionId(sessionId);
                    return cartRepository.save(newCart);
                });
    }
}
