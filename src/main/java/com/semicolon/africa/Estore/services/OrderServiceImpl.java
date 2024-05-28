package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.*;
import com.semicolon.africa.Estore.data.repositories.Orders;
import com.semicolon.africa.Estore.dtos.request.CreateAddressRequest;
import com.semicolon.africa.Estore.dtos.request.CreateBillingFormatRequest;
import com.semicolon.africa.Estore.dtos.request.PlaceOrderRequest;
import com.semicolon.africa.Estore.dtos.response.PlaceOrderResponse;
import com.semicolon.africa.Estore.exceptions.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.semicolon.africa.Estore.utils.Mapper.map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ItemService itemService;
    @Autowired
    private BillingFormationService billingFormationService;
    @Autowired
    private AddressesServices addressesServices;
    @Autowired
    @Lazy
    private CustomerService customerService;
    @Autowired
    private ProductService productService;
    @Autowired
    @Lazy
    private SellerService sellerService;
    @Autowired
    private Orders orders;
    @Autowired
    private CartServices cartServices;
    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderRequest placeOrderRequest, CreateAddressRequest createAddressRequest, CreateBillingFormatRequest createBillingFormatRequest) {
        Item item = itemService.findItem(placeOrderRequest.getItemId());
        BigDecimal amount = cartServices.calculateTotalAmountOfItem(placeOrderRequest.getCustomerId());
        Address address = addressesServices.save(map(createAddressRequest));
        BillingInformation billingInformation = billingFormationService.save(map(createBillingFormatRequest,address));
        Order order = map(placeOrderRequest,item,billingInformation,amount);
        order = orders.save(order);
        addOrderToListOfCustomerAndSeller(order);
        return map(order);
    }

    @Override
    public Order saveOrder(Order order) {
        return orders.save(order);
    }

    @Override
    public Long countOrderFor(Long id) {
       List<Order> orderList = orders.findOrderByCustomerId(id);
        if(orderList.isEmpty()) throw new OrderNotFoundException("No Order Found ");
        return (long)orderList.size();
    }

    private void addOrderToListOfCustomerAndSeller(Order order) {
        long sellerId = productService.findProductBy(order.getItem().getProductId()).getSellerId();
        sellerService.recieveOrder(order,sellerId);
        customerService.receiverOrder(order,order.getCustomerId());
    }
}
