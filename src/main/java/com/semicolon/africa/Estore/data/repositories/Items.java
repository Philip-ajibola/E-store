package com.semicolon.africa.Estore.data.repositories;

import com.semicolon.africa.Estore.data.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Items extends JpaRepository<Item, Long> {
}
