package com.example.repository;

import com.example.model.Cart;
import com.example.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {


    Optional<CartItem> findByCartAndProductId(Cart cart, Long productId);

   // Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
}
