package com.semicolon.africa.Estore.data.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CreditCardInformation {
    @Column(nullable = false)
    private String cardCcv;
    @Column(nullable = false)
    private String cardNumber;
    @Column(nullable = false)
    private String expiryDate;
    @Column(nullable = false)
    private CardType cardType;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
