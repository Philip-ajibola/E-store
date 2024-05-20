package com.semicolon.africa.Estore.utils;

import com.semicolon.africa.Estore.data.models.Seller;
import com.semicolon.africa.Estore.dtos.request.RegisterSellerRequest;
import com.semicolon.africa.Estore.dtos.response.RegisterSellerResponse;

public class Mapper {
    public  static RegisterSellerResponse map(Seller seller) {
        RegisterSellerResponse registerSellerResponse = new RegisterSellerResponse();
        registerSellerResponse.setSeller_name(seller.getUsername());
        registerSellerResponse.setId(seller.getId());
        return registerSellerResponse;
    }

    public static Seller map(RegisterSellerRequest request) {
        Seller seller = new Seller();
        seller.setEmail(request.getSeller_email());
        seller.setUsername(request.getSeller_name());
        seller.setPhone(request.getSeller_phone());
        seller.setPassword(request.getPassword());
        return seller;
    }
}
