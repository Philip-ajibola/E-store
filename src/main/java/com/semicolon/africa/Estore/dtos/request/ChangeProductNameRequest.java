package com.semicolon.africa.Estore.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeProductNameRequest {
    private long productId;
    @NotNull(message = "Provide a Name")
    private String productNewName;
}
