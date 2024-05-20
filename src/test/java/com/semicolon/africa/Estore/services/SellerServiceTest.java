package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.repositories.Sellers;
import com.semicolon.africa.Estore.dtos.request.RegisterSellerRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class SellerServiceTest {
    @Autowired
    private Sellers sellers;
    @Autowired
    private SellerService sellerService;
    @Test
    public void testThatSellerCanRegister(){
        RegisterSellerRequest registerSellerRequest = new RegisterSellerRequest();
        registerSellerRequest.setSeller_name("Seller_name");
        registerSellerRequest.setSeller_email("Seller_email");
        registerSellerRequest.setSeller_phone("Seller_phone");
        registerSellerRequest.setPassword("Seller_password");
        sellerService.registerSeller(registerSellerRequest);
        assertEquals("Seller_name",sellers.findByEmail(registerSellerRequest.getSeller_email()).getUsername());
    }
}