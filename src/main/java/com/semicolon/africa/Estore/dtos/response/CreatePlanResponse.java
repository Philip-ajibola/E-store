package com.semicolon.africa.Estore.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePlanResponse {

    private Boolean status;
    private String message;
    private Data data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class Data {

        private String name;

        private String amount;

        private String interval;

        private String integration;

        private String planCode;

        private String sendInvoices;

        private String sendSms;

        private String currency;

       private String id;

        private String createdAt;

        private String updatedAt;

    }
}
