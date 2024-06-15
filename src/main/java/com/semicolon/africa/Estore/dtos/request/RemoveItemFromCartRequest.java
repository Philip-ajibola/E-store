package com.semicolon.africa.Estore.dtos.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RemoveItemFromCartRequest {
    private long itemId;

    @Setter
    @Getter
    public static class CancelOrderRequest {
        private Long customerId;
        private Long orderId;
    }
}
