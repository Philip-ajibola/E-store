package com.semicolon.africa.Estore.data.repositories;

import com.semicolon.africa.Estore.data.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Admins extends JpaRepository<Admin,Long> {

    boolean existsByEmail(String email);

    Admin findByEmail(String email);
}
