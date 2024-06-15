package com.semicolon.africa.Estore.data.repositories;

import com.semicolon.africa.Estore.data.models.PaymentPaystack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayStackPaymentRepository extends JpaRepository<PaymentPaystack, Long> {
}
