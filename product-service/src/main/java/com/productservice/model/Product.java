package com.productservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "price", precision = 19, scale = 2)
    private BigDecimal price;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "weight_g")
    private Integer weight;

//    @Column(name = "sku", unique = true, length = 100) // Stock Keeping Unit
//    private String sku;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE) // LAZY fetch is generally preferred for performance
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}