package com.semicolon.africa.Estore.data.repositories;

import com.semicolon.africa.Estore.data.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Carts extends JpaRepository<Cart, Long> {
}
