package com.semicolon.africa.Estore.data.repositories;

import com.semicolon.africa.Estore.data.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Customers extends JpaRepository<Customer, Long> {
}
