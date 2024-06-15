package com.semicolon.africa.Estore.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentVerificationResponse {

    private String status;

    private String message;

    private Data data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Data{

        private String status;

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

        private Date updatedOn = new Date();
    }
}