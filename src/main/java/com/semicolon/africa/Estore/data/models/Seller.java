package com.semicolon.africa.Estore.data.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


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
}
