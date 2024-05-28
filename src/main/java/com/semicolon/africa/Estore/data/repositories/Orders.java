package com.semicolon.africa.Estore.data.repositories;

import com.semicolon.africa.Estore.data.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Orders extends JpaRepository<Order,Long> {
    List<Order> findOrderByCustomerId(Long id);
}

