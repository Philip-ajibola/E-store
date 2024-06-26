package com.semicolon.africa.Estore.data.repositories;

import com.semicolon.africa.Estore.data.models.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Sellers extends JpaRepository<Seller, Long> {
 Seller findByEmail(String email);
 boolean existsByEmail(String email);
}
