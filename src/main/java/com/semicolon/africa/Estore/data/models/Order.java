package com.semicolon.africa.Estore.data.models;

import com.semicolon.africa.Estore.services.OrderService;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "order_table")
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private OrderStatus status;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Seller seller;
    @OneToOne
    private Cart cart;
    private BigDecimal amount;
    @OneToOne
    private BillingInformation billingInformation;
     private LocalDate orderedDate;
     private LocalDate deliveryDate;
}
