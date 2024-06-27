package com.semicolon.africa.Estore.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public  class CancelOrderRequest {
    private Long customerId;
    private Long orderId;
}
