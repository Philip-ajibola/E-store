package com.semicolon.africa.Estore.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="items")
public class Item {
    @Column(nullable = false)
    private Integer quantity;
    @OneToOne
    @JoinColumn(nullable = false)
    private Product product;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
