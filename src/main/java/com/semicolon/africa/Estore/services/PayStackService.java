package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.dtos.request.CreatePlanDto;
import com.semicolon.africa.Estore.dtos.request.InitializePaymentDto;
import com.semicolon.africa.Estore.dtos.response.CreatePlanResponse;
import com.semicolon.africa.Estore.dtos.response.InitializePaymentResponse;
import com.semicolon.africa.Estore.dtos.response.PaymentVerificationResponse;

public interface PayStackService {
    CreatePlanResponse createPlan(CreatePlanDto createPlanDto) throws Exception;
    InitializePaymentResponse initializePayment(InitializePaymentDto initializePaymentDto);
    PaymentVerificationResponse paymentVerification(String reference, Long id) throws Exception;

}
