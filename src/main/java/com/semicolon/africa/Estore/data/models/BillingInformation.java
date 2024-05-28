package com.semicolon.africa.Estore.data.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class BillingInformation {
    @Column( nullable = false)
    private String receiverPhoneNumber;
    @Column( nullable = false)
    private String receiversName;
    @OneToOne
    private Address deliveryAddress;
    @OneToOne
    private CreditCardInformation creditCardInformation;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
