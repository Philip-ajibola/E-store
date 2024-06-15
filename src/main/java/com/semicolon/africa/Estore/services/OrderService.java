package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.Order;
import com.semicolon.africa.Estore.dtos.request.CreateAddressRequest;
import com.semicolon.africa.Estore.dtos.request.CreateBillingFormatRequest;
import com.semicolon.africa.Estore.dtos.request.PlaceOrderRequest;
import com.semicolon.africa.Estore.dtos.response.OrderResponse;
import com.semicolon.africa.Estore.dtos.response.PlaceOrderResponse;
import jakarta.mail.MessagingException;

import java.util.List;

public interface OrderService {
    PlaceOrderResponse placeOrder(PlaceOrderRequest placeOrderRequest, CreateAddressRequest createAddressRequest, CreateBillingFormatRequest createBillingFormatRequest) throws MessagingException;

    Order saveOrder(Order order);

    Long countOrderFor(Long id);

    void deleteOrderById(Long orderId);

    List<OrderResponse> getOrderFor(Long id);


    OrderResponse confirmOrder(long orderId);
}
