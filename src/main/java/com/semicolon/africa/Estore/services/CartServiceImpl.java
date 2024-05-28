package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.Item;
import com.semicolon.africa.Estore.data.repositories.Carts;
import com.semicolon.africa.Estore.dtos.request.AddItemToCartRequest;
import com.semicolon.africa.Estore.dtos.request.RemoveItemFromCartRequest;
import com.semicolon.africa.Estore.dtos.response.AddItemResponse;
import com.semicolon.africa.Estore.exceptions.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements CartServices{
    @Autowired
    private Carts carts;
    @Autowired
    private ItemService itemService;
    @Override
    public AddItemResponse addItemToCart(AddItemToCartRequest addItemToCartRequest) {
        return itemService.addItem(addItemToCartRequest);
    }

    @Override
    public long countCustomerItem(long customerId) {
        return itemService.countCustomerItem(customerId);
    }

    @Override
    public void removeItemFromCart(RemoveItemFromCartRequest removeItemFromCartRequest) {
        itemService.deleteItem(removeItemFromCartRequest);
    }

    @Override
    public List<Item> viewCart(Long id) {
        return itemService.getItems(id);
    }

    @Override
    public BigDecimal calculateTotalAmountOfItem(long customerId) {
        List<Item> items = itemService.getItems(customerId);
        if(items.isEmpty())throw new ItemNotFoundException("No Items found");
        return BigDecimal.valueOf(items.stream().mapToDouble(item-> Double.parseDouble(String.valueOf(item.getProductPrice().multiply(BigDecimal.valueOf(item.getQuantity()))))).sum());
    }
}
