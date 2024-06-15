package com.semicolon.africa.Estore.data.repositories;

import com.semicolon.africa.Estore.data.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Orders extends JpaRepository<Order,Long> {

    @Query("SELECT o FROM Order o WHERE o.customer.id =:customerId")
    List<Order> findOrderByCustomerId( Long customerId);

}

