package com.semicolon.africa.Estore.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Entity
@Getter
@Setter
public class Product {
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private String productDescription;
    @Column(nullable = false)
    private BigDecimal productPrice;
    @Column(nullable = false)
    private ProductCategory category;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
