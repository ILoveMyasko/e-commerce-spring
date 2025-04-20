package com.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name="cart_items", indexes = {
        @Index(name = "idx_cartitem_cart_product", columnList = "cart_id, product_id", unique = true) })
public class CartItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @Column(name = "product_id", nullable = false)
    private long productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    //TODO kafka Listening for price updates
    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    //race condition simple safety
    @Version
    private Long version;
}
