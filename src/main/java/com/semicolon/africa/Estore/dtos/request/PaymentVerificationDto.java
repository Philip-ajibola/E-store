package com.semicolon.africa.Estore.dtos.request;

import com.semicolon.africa.Estore.data.models.Customer;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentVerificationDto {

    private Customer user;

    private String reference;

    private BigDecimal amount;

    private String gatewayResponse;

    private String paidAt;

    private String createdAt;

    private String channel;

    private String currency;

    private String ipAddress;

    private String pricingPlanType;

    private Date createdOn = new Date();
}
