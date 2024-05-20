package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.dtos.request.RegisterSellerRequest;
import com.semicolon.africa.Estore.dtos.response.RegisterSellerResponse;

public interface SellerService {
    RegisterSellerResponse registerSeller(RegisterSellerRequest request);
}
