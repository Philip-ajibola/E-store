package com.semicolon.africa.Estore.constant;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
public class APIConstants {
    public static final int STATUS_CODE_OK = 200;
    public static final int STATUS_CODE_CREATED = 201;
    public static final String PAYSTACK_INIT = "https://api.paystack.co/transaction/initialize";
    public static final String PAYSTACK_VERIFY = "https://api.paystack.co/transaction/verify/";
    public static final String PAYSTACK_INITIALIZE_PAY = "https://api.paystack.co/transaction/initialize";
}
