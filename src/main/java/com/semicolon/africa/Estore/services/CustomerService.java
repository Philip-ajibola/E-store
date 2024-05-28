package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.Customer;
import com.semicolon.africa.Estore.data.models.Order;
import com.semicolon.africa.Estore.dtos.request.*;
import com.semicolon.africa.Estore.dtos.response.AddProductResponse;
import com.semicolon.africa.Estore.dtos.response.PlaceOrderResponse;
import com.semicolon.africa.Estore.dtos.response.RegisterCustomerResponse;

import java.util.List;

public interface CustomerService {

    long count();

    RegisterCustomerResponse registerCustomer(RegisterCustomerRequest request);

    String login(LoginRequest loginRequest);

    Customer findCustomer(String email);

    String logout(LogoutRequest loginRequest);

    AddProductResponse searchForProductByName(SearchForProductByNameRequest searchRequest);

    List<AddProductResponse> searchForProductByCategory(SearchForProductByCategoryRequest searchRequest);

    PlaceOrderResponse placeOrder(PlaceOrderRequest placeOrderRequest,CreateAddressRequest createAddressRequest,CreateBillingFormatRequest createBillingFormatRequest);

    void receiverOrder(Order order, long customerId);
}

