package com.semicolon.africa.Estore.data.models;

public enum PricingPlanType {
    BASIC("BASIC"),
    STANDARD("STANDARD"),
    PREMIUM("PREMIUM");

    private String value;
    PricingPlanType(String value) {
        this.value = value;
    }
}
