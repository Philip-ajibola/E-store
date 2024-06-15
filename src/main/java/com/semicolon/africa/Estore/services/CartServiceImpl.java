package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.Cart;
import com.semicolon.africa.Estore.data.models.Customer;
import com.semicolon.africa.Estore.data.models.Item;
import com.semicolon.africa.Estore.data.repositories.Carts;
import com.semicolon.africa.Estore.dtos.request.AddItemToCartRequest;
import com.semicolon.africa.Estore.dtos.request.RemoveItemFromCartRequest;
import com.semicolon.africa.Estore.dtos.response.AddItemResponse;
import com.semicolon.africa.Estore.exceptions.ItemNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements CartServices{
    @Autowired
    private Carts carts;
    @Autowired
    @Lazy
    private CustomerService customerService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ModelMapper mapper;
    @Override
    public AddItemResponse addItemToCart(AddItemToCartRequest addItemToCartRequest, Customer customer) {
        Item item =  itemService.addItem(addItemToCartRequest);
        Cart cart = carts.findByCustomerId(customer.getId());
        cart.getItems().add(item);
        carts.save(cart);
        customer.setCart(cart);
        customerService.save(customer);
        return mapper.map(customer.getCart(),AddItemResponse.class);
    }

    @Override
    public long countCustomerItem(long customerId) {
        return itemService.countCustomerItem(customerId);
    }

    @Override
    public void removeItemFromCart(RemoveItemFromCartRequest removeItemFromCartRequest,Customer customer) {
        customer.getCart().getItems().remove(itemService.findItem(removeItemFromCartRequest.getItemId()));
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

    @Override
    public Cart findCart(Long cartId) {
        return carts.findById(cartId).orElseThrow(()->new ItemNotFoundException("No Cart Found"));
    }

    @Override
    public Cart save(Cart cart) {
        return carts.save(cart);
    }
}
