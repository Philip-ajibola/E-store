package com.semicolon.africa.Estore.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semicolon.africa.Estore.constant.APIConstants;
import com.semicolon.africa.Estore.data.models.Customer;
import com.semicolon.africa.Estore.data.models.PaymentPaystack;
import com.semicolon.africa.Estore.data.models.PricingPlanType;
import com.semicolon.africa.Estore.data.repositories.Customers;
import com.semicolon.africa.Estore.data.repositories.PayStackPaymentRepository;
import com.semicolon.africa.Estore.dtos.request.CreatePlanDto;
import com.semicolon.africa.Estore.dtos.request.InitializePaymentDto;
import com.semicolon.africa.Estore.dtos.response.CreatePlanResponse;
import com.semicolon.africa.Estore.dtos.response.InitializePaymentResponse;
import com.semicolon.africa.Estore.dtos.response.PaymentVerificationResponse;
import jakarta.transaction.Transactional;
import lombok.Value;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;

import static com.semicolon.africa.Estore.constant.APIConstants.*;

@Service

public class PaystackServiceImpl implements PayStackService {
    @Autowired
    private  PayStackPaymentRepository paystackPaymentRepository;
    @Autowired
    private Customers customerRepository;

    private String getSecretKey(){
        Properties properties = new Properties();
        try(InputStream input = new FileInputStream("C:\\Users\\Dell\\Downloads\\E-store\\src\\main\\resources\\config.properties")){
            properties.load(input);
            return properties.getProperty("paystack.api.key");
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public CreatePlanResponse createPlan(CreatePlanDto createPlanDto) throws Exception {
        CreatePlanResponse createPlanResponse = null;

        try {
            Gson gson = new Gson();
            StringEntity postingString = new StringEntity(gson.toJson(createPlanDto));
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(APIConstants.PAYSTACK_INIT);
            post.setEntity(postingString);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Authorization", "Bearer " + getSecretKey());
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(post);

            if (response.getStatusLine(). getStatusCode() == APIConstants.STATUS_CODE_CREATED) {

                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            } else {
                throw new Exception ("Paystack is unable to process payment at the moment " +
                        "or something wrong with request");
            }

            ObjectMapper mapper = new ObjectMapper();
            createPlanResponse = mapper.readValue(result.toString(), CreatePlanResponse.class);
        } catch(Throwable ex) {
            ex.printStackTrace();
        }
        return createPlanResponse;
    }

    @Override
    public InitializePaymentResponse initializePayment(InitializePaymentDto initializePaymentDto) {
        InitializePaymentResponse initializePaymentResponse = null;

        try {
            Gson gson = new Gson();
            StringEntity postingString = new StringEntity(gson.toJson(initializePaymentDto));
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(PAYSTACK_INITIALIZE_PAY);
            post.setEntity(postingString);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Authorization", "Bearer " + getSecretKey());
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(post);

            if (response.getStatusLine(). getStatusCode() == STATUS_CODE_OK) {

                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            } else {
                throw new Exception("Paystack is unable to initialize payment at the moment");
            }

            ObjectMapper mapper = new ObjectMapper();
            initializePaymentResponse = mapper. readValue(result.toString(), InitializePaymentResponse.class);
        } catch(Throwable ex) {
            ex.printStackTrace();
        }
        return initializePaymentResponse;
    }

    @Override
    @Transactional
    public PaymentVerificationResponse paymentVerification(String reference, String plan, Long id) throws  Exception {
        PaymentVerificationResponse paymentVerificationResponse = null;
        PaymentPaystack paymentPaystack = null;

        try{
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(PAYSTACK_VERIFY + reference);
            request.addHeader("Content-type", "application/json");
            request.addHeader("Authorization", "Bearer " + getSecretKey());
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(request);

            if (response.getStatusLine(). getStatusCode() == STATUS_CODE_OK) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;

                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            } else {
                throw new Exception("Paystack is unable to verify payment at the moment");
            }

            ObjectMapper mapper = new ObjectMapper();
            paymentVerificationResponse = mapper.readValue(result.toString(), PaymentVerificationResponse.class);

            if( paymentVerificationResponse == null || paymentVerificationResponse.getStatus().equals("false")) {
                throw new Exception("An error");
            } else if (paymentVerificationResponse. getData().getStatus().equals("success")) {

                Customer appUser = customerRepository.getById(id);
                PricingPlanType pricingPlanType = PricingPlanType.valueOf(plan.toUpperCase());

                paymentPaystack = PaymentPaystack.builder()
                        .customer(appUser)
                        .reference(paymentVerificationResponse.getData().getReference())
                        .amount(paymentVerificationResponse.getData().getAmount())
                        .gatewayResponse(paymentVerificationResponse.getData().getGatewayResponse())
                        .paidAt(paymentVerificationResponse.getData().getPaidAt())
                        .createdAt(paymentVerificationResponse.getData().getCreatedAt())
                        .channel(paymentVerificationResponse.getData().getChannel())
                        .currency(paymentVerificationResponse.getData().getCurrency())
                        .ipAddress(paymentVerificationResponse.getData().getIpAddress())
                        .planType(pricingPlanType)
                        .createdOn(new Date())
                        .build();
            }
        } catch (Exception ex) {
            throw new Exception("Paystack");
        }
        paystackPaymentRepository.save(paymentPaystack);
        return paymentVerificationResponse;
    }
}
