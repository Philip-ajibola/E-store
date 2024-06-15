package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.*;
import com.semicolon.africa.Estore.data.repositories.Orders;
import com.semicolon.africa.Estore.dtos.request.CreateAddressRequest;
import com.semicolon.africa.Estore.dtos.request.CreateBillingFormatRequest;
import com.semicolon.africa.Estore.dtos.request.PlaceOrderRequest;
import com.semicolon.africa.Estore.dtos.response.OrderResponse;
import com.semicolon.africa.Estore.dtos.response.PlaceOrderResponse;
import com.semicolon.africa.Estore.exceptions.EstoreExceptions;
import com.semicolon.africa.Estore.exceptions.OrderNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.semicolon.africa.Estore.utils.HTMLDesigns.sendOrderMessage;
import static com.semicolon.africa.Estore.utils.Mapper.map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    @Lazy
    private AdminServices adminServices;
    @Autowired
    private MailServices mailServices;
    @Autowired
    private BillingFormationService billingFormationService;
    @Autowired
    private AddressesServices addressesServices;
    @Autowired
    @Lazy
    private CustomerService customerService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private Orders orders;
    @Autowired
    private CartServices cartServices;
    @Override
    @Transactional
    public PlaceOrderResponse placeOrder(PlaceOrderRequest placeOrderRequest, CreateAddressRequest createAddressRequest, CreateBillingFormatRequest createBillingFormatRequest) throws MessagingException {
        Cart cart = cartServices.findCart(placeOrderRequest.getCartId());
        BigDecimal amount = cartServices.calculateTotalAmountOfItem(placeOrderRequest.getCustomerId());
        Address address = addressesServices.save(map(createAddressRequest));
        BillingInformation billingInformation = billingFormationService.save(map(createBillingFormatRequest,address));
        Order order = map(customerService.findCustomerBy(placeOrderRequest.getCustomerId()),cart,billingInformation,amount);

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

    @Override
    public void deleteOrderById(Long orderId) {
       Order order = orders.findById(orderId).orElseThrow(()->new OrderNotFoundException("No Order Found "));
       if(order == null) throw new OrderNotFoundException("No Order Found ");
       orders.deleteById(orderId);
    }

    @Override
    public List<OrderResponse> getOrderFor(Long id) {
        return orders.findOrderByCustomerId(id).stream().map((order)->mapper.map(order, OrderResponse.class)).toList();
    }

    @Override
    public OrderResponse confirmOrder(long orderId) {
        Order order = orders.findById(orderId).orElseThrow(()->new OrderNotFoundException("Order Not Found"));
        order.setStatus(OrderStatus.CONFIRMED);
        return mapper.map(order, OrderResponse.class);
    }

    private void addOrderToListOfCustomerAndSeller(Order order) throws MessagingException {
        customerService.customerOrders(order,order.getCustomer());
    }

}
