package com.semicolon.africa.Estore.services;

import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.semicolon.africa.Estore.data.models.Order;
import com.semicolon.africa.Estore.data.models.Seller;
import com.semicolon.africa.Estore.dtos.request.*;
import com.semicolon.africa.Estore.dtos.response.AddProductResponse;
import com.semicolon.africa.Estore.dtos.response.EditProductResponse;
import com.semicolon.africa.Estore.dtos.response.LoginResponse;
import com.semicolon.africa.Estore.dtos.response.RegisterSellerResponse;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;

public interface SellerService {
    RegisterSellerResponse registerSeller(RegisterSellerRequest request) throws MessagingException;
    LoginResponse login(LoginRequest loginRequest);
    String logout(LogoutRequest logoutRequest);
    Seller  findSeller(String sellerEmail);

    AddProductResponse addProductToStore(AddProductRequest addProduct) throws IOException;

    String deleteProduct(DeleteProductRequest deleteProductRequest);

     EditProductResponse editProduct(Long id, JsonPatch patch) throws JsonPatchException;

    long count();

    String deactivateAccount(Long id);

    List<AddProductResponse> findAllProductAdded();

    Seller findSellerBy(long sellerId);

    void recieveOrder(Order order, long sellerId);
    void confirmOrder(long orderId, long sellerId);
    void cancelOrder(long orderId, long sellerId);

    void deleteAll();

    List<Seller> findAll();
}
