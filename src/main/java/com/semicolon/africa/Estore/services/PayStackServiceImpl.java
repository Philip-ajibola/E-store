package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.dtos.request.CreatePlanDto;
import com.semicolon.africa.Estore.dtos.request.InitializePaymentDto;
import com.semicolon.africa.Estore.dtos.response.CreatePlanResponse;
import com.semicolon.africa.Estore.dtos.response.InitializePaymentResponse;
import com.semicolon.africa.Estore.dtos.response.PaymentVerificationResponse;
import org.springframework.stereotype.Service;

@Service
public class PayStackServiceImpl implements PayStackService{
    @Override
    public CreatePlanResponse createPlan(CreatePlanDto createPlanDto) throws Exception {
        return null;
    }

    @Override
    public InitializePaymentResponse initializePayment(InitializePaymentDto initializePaymentDto) {
        return null;
    }

    @Override
    public PaymentVerificationResponse paymentVerification(String reference, String plan, Long id) throws Exception {
        return null;
    }
}
