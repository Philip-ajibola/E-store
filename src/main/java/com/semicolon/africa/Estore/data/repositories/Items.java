package com.semicolon.africa.Estore.data.repositories;

import com.semicolon.africa.Estore.data.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Items extends JpaRepository<Item, Long> {
    List<Item> findItemByCustomerId(long customerId);
}
