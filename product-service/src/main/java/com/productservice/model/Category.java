package com.productservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long categoryId;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    //TODO add category hierarchy

}
