package com.semicolon.africa.Estore.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private String amount;
    private String email;
    private long customerId;
    private long orderId;
}
