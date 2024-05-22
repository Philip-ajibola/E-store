package com.semicolon.africa.Estore.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LogoutRequest {
    @NotNull(message = "email can't be null")
    private String email;
    @NotNull(message = "Provide a password")
    private String password;
}
