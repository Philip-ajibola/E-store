package com.semicolon.africa.Estore.data.repositories;

import com.semicolon.africa.Estore.data.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface Customers extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);

}
