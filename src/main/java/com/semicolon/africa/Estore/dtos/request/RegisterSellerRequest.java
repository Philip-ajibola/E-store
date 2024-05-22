package com.semicolon.africa.Estore.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterSellerRequest {
    @NotNull(message = "name can't be null")
    private String seller_name;
    @NotNull(message = "email can't be null")
    private String seller_email;
    @NotNull(message = "phone number can't be null ")
    private String seller_phone;
    @NotNull(message = "provide a password ")
    private String password;
}
