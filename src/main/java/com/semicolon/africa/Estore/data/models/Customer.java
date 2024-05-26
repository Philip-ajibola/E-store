package com.semicolon.africa.Estore.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="\"customer\"")
public class Customer {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private String email;
    private boolean isActive;
    private String phone;
    @OneToOne
    private Address address;
    @OneToOne
    private Cart cart;
}
