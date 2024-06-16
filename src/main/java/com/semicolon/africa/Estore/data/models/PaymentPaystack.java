package com.semicolon.africa.Estore.data.models;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentPaystack {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ManyToOne
    private Customer customer;
    private String reference;
    private String channel;
    private BigDecimal amount;
    private String gatewayResponse;
    private String paidAt;
    private String createdAt;
    private String currency ;
    private String ipAddress;
    @Enumerated(EnumType.STRING)
    private PricingPlanType planType;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

}
