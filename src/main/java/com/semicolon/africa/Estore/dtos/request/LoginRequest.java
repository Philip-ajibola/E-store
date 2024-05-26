package com.semicolon.africa.Estore.dtos.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
