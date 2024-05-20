package com.semicolon.africa.Estore.data.repositories;

import com.semicolon.africa.Estore.data.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface Products extends JpaRepository<Product, Long> {
}
