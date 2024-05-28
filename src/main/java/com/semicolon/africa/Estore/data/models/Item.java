package com.semicolon.africa.Estore.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name="items")
public class Item {
    private Integer quantity;
    private BigDecimal productPrice = new BigDecimal("0.0");
    @JoinColumn
    private Long productId;
    @JoinColumn
    private long customerId;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
