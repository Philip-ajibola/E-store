package com.semicolon.africa.Estore.data.repositories;

import com.semicolon.africa.Estore.data.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Addresses extends JpaRepository<Address, Long> {
}
