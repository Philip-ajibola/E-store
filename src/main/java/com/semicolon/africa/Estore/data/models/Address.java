package com.semicolon.africa.Estore.data.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="\"address\"")
public class Address {
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String houseNumber;
    @Column
    private City city;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
