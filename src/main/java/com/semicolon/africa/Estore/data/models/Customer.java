package com.semicolon.africa.Estore.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(fetch = FetchType.EAGER)
    private List<Order> listOfOrders = new ArrayList<>();
}
