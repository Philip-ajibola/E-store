package com.semicolon.africa.Estore.dtos.request;

import lombok.Data;

@Data
public class CreateBillingFormatRequest {
    private String receiversName;
    private String receiversActiveContact;
}
