package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.Order;
import com.semicolon.africa.Estore.dtos.request.CreateAddressRequest;
import com.semicolon.africa.Estore.dtos.request.CreateBillingFormatRequest;
import com.semicolon.africa.Estore.dtos.request.PlaceOrderRequest;
import com.semicolon.africa.Estore.dtos.response.PlaceOrderResponse;

public interface OrderService {
    PlaceOrderResponse placeOrder(PlaceOrderRequest placeOrderRequest, CreateAddressRequest createAddressRequest, CreateBillingFormatRequest createBillingFormatRequest);

    Order saveOrder(Order order);

    Long countOrderFor(Long id);
}
