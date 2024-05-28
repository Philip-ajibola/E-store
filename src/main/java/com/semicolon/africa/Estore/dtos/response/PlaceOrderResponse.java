package com.semicolon.africa.Estore.dtos.response;

import com.semicolon.africa.Estore.data.models.OrderStatus;
import lombok.Data;

@Data
public class PlaceOrderResponse {
    private long orderId;
    private OrderStatus status;
}
