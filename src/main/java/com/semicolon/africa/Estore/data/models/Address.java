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
    private String cityName;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String houseNumber;
    @Column(nullable = false)
    private String countryName;
    @Column
    private String state;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
