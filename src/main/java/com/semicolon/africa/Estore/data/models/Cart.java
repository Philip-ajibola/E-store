package com.semicolon.africa.Estore.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@Entity
public class Cart {
    @OneToMany
    private List<Item> items;
    @Id
    private Long id;

}
