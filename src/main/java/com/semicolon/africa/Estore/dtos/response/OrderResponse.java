package com.semicolon.africa.Estore.dtos.response;

import com.semicolon.africa.Estore.data.models.BillingInformation;
import com.semicolon.africa.Estore.data.models.Cart;
import com.semicolon.africa.Estore.data.models.OrderStatus;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class OrderResponse {
    private Long id;
    private OrderStatus status;
    private Long customerId;
    private Cart cart;
    private BigDecimal amount;
    private BillingInformation billingInformation;
    private LocalDate orderedDate;
    private LocalDate deliveryDate;
}
