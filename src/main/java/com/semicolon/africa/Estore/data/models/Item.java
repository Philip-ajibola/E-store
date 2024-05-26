package com.semicolon.africa.Estore.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="items")
public class Item {
    private Integer quantity;
    @JoinColumn
    private Long productId;
    @JoinColumn
    private long customerId;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
