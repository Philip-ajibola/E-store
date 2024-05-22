package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.Seller;
import com.semicolon.africa.Estore.dtos.request.*;
import com.semicolon.africa.Estore.dtos.response.AddProductResponse;
import com.semicolon.africa.Estore.dtos.response.RegisterSellerResponse;

public interface SellerService {
    RegisterSellerResponse registerSeller(RegisterSellerRequest request);
    String login(LoginRequest loginRequest);
    String logout(LogoutRequest logoutRequest);
    Seller  findSeller(String sellerEmail);

    AddProductResponse addProductToStore(AddProductRequest addProduct);

    String deleteProduct(DeleteProductRequest deleteProductRequest);

    String changeProductPrice(ChangeProductPriceRequest request);
}
