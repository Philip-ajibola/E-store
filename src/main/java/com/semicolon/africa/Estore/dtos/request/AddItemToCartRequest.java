package com.semicolon.africa.Estore.dtos.request;

import lombok.Data;

@Data
public class AddItemToCartRequest {
    private long productId;
    private int quantity;
    private long customerId;
}
