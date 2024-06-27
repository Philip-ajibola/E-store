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
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToOne
    private Cart cart;
    private BigDecimal amount;
    @OneToOne
    private BillingInformation billingInformation;
     private LocalDate orderedDate;
     private LocalDate deliveryDate;
}
