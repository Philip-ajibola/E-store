package com.semicolon.africa.Estore.dtos.request;

import com.semicolon.africa.Estore.data.models.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterAdminRequest {
    private String email;
    private String password;
    private String username;
    private Role role;
    private String phoneNumber;

}
