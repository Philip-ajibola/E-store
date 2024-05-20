package com.semicolon.africa.Estore.dtos.request;

import lombok.Data;

@Data
public class RegisterSellerRequest {
    private String seller_name;
    private String seller_email;
    private String seller_phone;
    private String password;
}
