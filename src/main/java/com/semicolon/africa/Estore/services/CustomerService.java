package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.Customer;
import com.semicolon.africa.Estore.data.models.Order;
import com.semicolon.africa.Estore.dtos.request.*;
import com.semicolon.africa.Estore.dtos.response.*;
import jakarta.mail.MessagingException;

import java.util.List;

public interface CustomerService {

    long count();

    RegisterCustomerResponse registerCustomer(RegisterCustomerRequest request) throws MessagingException;

    LoginResponse login(LoginRequest loginRequest);

    Customer findCustomer(String email);

    String logout(LogoutRequest loginRequest);

    List<AddProductResponse> searchForProductByName(SearchForProductByNameRequest searchRequest);

    List<AddProductResponse> searchForProductByCategory(SearchForProductByCategoryRequest searchRequest);

    PlaceOrderResponse placeOrder(PlaceOrderRequest placeOrderRequest,CreateAddressRequest createAddressRequest,CreateBillingFormatRequest createBillingFormatRequest) throws MessagingException;

    void customerOrders(Order order, Customer customer);
    AddItemResponse addItemToCart(AddItemToCartRequest addItemToCartRequest,Long customerId);
    void removeItemFromCart(RemoveItemFromCartRequest removeItemFromCartRequest,Long customerId);

    CancelOrderResponse cancelOrder(RemoveItemFromCartRequest.CancelOrderRequest cancelOrderRequest);

    List<OrderResponse> getOrder(Long id);

    Customer save(Customer customer);

    Customer findCustomerBy(Long customerId);

    List<OrderResponse> getListOfOrders(Long customerId);


}


