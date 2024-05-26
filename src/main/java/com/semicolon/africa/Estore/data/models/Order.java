package com.semicolon.africa.Estore.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Order {
    @Id
    private Long id;
    @OneToOne
    private Address deliveryAddress;
    @OneToOne
    private Product product;
    private BigDecimal amount;
}
