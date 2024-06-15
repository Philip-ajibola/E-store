package com.semicolon.africa.Estore.dtos.request;

import lombok.Data;

@Data
public class PlaceOrderRequest {
    private long customerId;
    private Long cartId;
  //  private long sellerId;
}
