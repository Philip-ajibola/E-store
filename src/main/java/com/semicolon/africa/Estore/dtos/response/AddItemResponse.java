package com.semicolon.africa.Estore.dtos.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddItemResponse {
    private Long cartId;
    private Long itemId;
}
