package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.BillingInformation;
import com.semicolon.africa.Estore.data.repositories.BillingInformations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillingFormatServiceImpl implements BillingFormationService{
    @Autowired
    private BillingInformations billingInformations;
    @Override
    public BillingInformation save(BillingInformation billingInformation) {
        return billingInformations.save(billingInformation);
    }
}
