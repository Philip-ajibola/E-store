package com.semicolon.africa.Estore.dtos.request;

import lombok.Data;

@Data
public class PlaceOrderRequest {
    private long customerId;
    private long itemId;
}
