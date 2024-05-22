package com.semicolon.africa.Estore.controller;

import com.semicolon.africa.Estore.dtos.request.RegisterSellerRequest;
import com.semicolon.africa.Estore.dtos.response.RegisterSellerResponse;
import com.semicolon.africa.Estore.services.SellerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/sellers")
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @PostMapping("/sign-up")
    public ResponseEntity<RegisterSellerResponse> signUp(@RequestBody @Valid RegisterSellerRequest request) {
        var result = sellerService.registerSeller(request);
        return ResponseEntity.ok(result);
    }
}
