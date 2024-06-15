package com.semicolon.africa.Estore.dtos.request;

import com.semicolon.africa.Estore.data.models.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserRequest {
    @NotNull(message = "name can't be null")
    private String customer_name;
    @NotNull(message = "email can't be null")
    private String customer_email;
    @NotNull(message = "phone number can't be null ")
    private String customer_phone;
    @NotNull(message = "provide a password ")
    private String password;
    private Role role;
}
