package com.semicolon.africa.Estore.data.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
@Table( name ="\"seller\"")
public class Seller {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private boolean isActive = false;
    @OneToOne
    private Address address;
    @OneToMany(fetch = FetchType.EAGER)
    List<Order> listOfOrders = new ArrayList<>();
}
