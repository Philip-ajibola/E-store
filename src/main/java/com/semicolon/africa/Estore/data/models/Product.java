package com.semicolon.africa.Estore.data.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Entity
@Getter
@Setter
public class Product {
    @NotNull(message = "ProductName can't be null")
    private String productName;
    private String productDescription;
    @NotNull(message = "Provide a price for the product")
    private BigDecimal productPrice;
    @NotNull(message = "Provide a category")
    private ProductCategory category;
    @JoinColumn
    private long sellerId;
    @Id
    @GeneratedValue
    private Long id;
}
