package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.Cart;
import com.semicolon.africa.Estore.data.models.Customer;
import com.semicolon.africa.Estore.data.models.Item;
import com.semicolon.africa.Estore.dtos.request.AddItemToCartRequest;
import com.semicolon.africa.Estore.dtos.request.RemoveItemFromCartRequest;
import com.semicolon.africa.Estore.dtos.response.AddItemResponse;
import org.springframework.beans.MutablePropertyValues;

import java.math.BigDecimal;
import java.util.List;

public interface CartServices {
    AddItemResponse addItemToCart(AddItemToCartRequest addItemToCartRequest, Customer customer);

    long countCustomerItem(long customerId);


    void removeItemFromCart(RemoveItemFromCartRequest removeItemFromCartRequest, Customer customer);

    List<Item> viewCart(Long id);

    BigDecimal calculateTotalAmountOfItem(long customerId);

    Cart findCart(Long cartId);

    Cart save(Cart cart);
}

