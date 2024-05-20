package com.semicolon.africa.Estore.data.repositories;

import com.semicolon.africa.Estore.data.models.CreditCardInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardsInformation extends JpaRepository<CreditCardInformation, Long> {
}
