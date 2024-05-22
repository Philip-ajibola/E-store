package com.semicolon.africa.Estore.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChangeProductPriceRequest {
    private long productId;
    @NotNull(message = "Provide A New Price")
    private BigDecimal newPrice;

}
