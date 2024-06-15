package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.Item;
import com.semicolon.africa.Estore.dtos.request.AddItemToCartRequest;
import com.semicolon.africa.Estore.dtos.request.RemoveItemFromCartRequest;

import java.util.List;

public interface ItemService {
    Item addItem(AddItemToCartRequest addItemToCartRequest);

     long count();

    Item findItem(long itemId);

    long countCustomerItem(long customerId);

    void deleteItem(RemoveItemFromCartRequest removeItemFromCartRequest);

    List<Item> getItems(long customerId);
}
