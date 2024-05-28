package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.*;
import com.semicolon.africa.Estore.data.repositories.Customers;
import com.semicolon.africa.Estore.dtos.request.*;
import com.semicolon.africa.Estore.dtos.response.AddProductResponse;
import com.semicolon.africa.Estore.dtos.response.PlaceOrderResponse;
import com.semicolon.africa.Estore.dtos.response.RegisterCustomerResponse;
import com.semicolon.africa.Estore.exceptions.InvalidPasswordException;
import com.semicolon.africa.Estore.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.semicolon.africa.Estore.utils.Mapper.map;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private Customers customers;
    @Autowired
    @Lazy
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Override
    public long count() {
        return customers.count();
    }

    @Override
    public RegisterCustomerResponse registerCustomer(RegisterCustomerRequest request) {
        Customer customer = customers.save(map(request));
        return map(customer);
    }

    @Override
    public String login(LoginRequest loginRequest) {
        Customer customer = findCustomer(loginRequest.getEmail());
        customer.setActive(true);
        customers.save(customer);
        return "Login successful";
    }
    @Override
    public Customer findCustomer(String email) {
        Customer customer = customers.findByEmail(email);
        if(customer == null) throw new UserNotFoundException("Could not find customer");
        return customer;
    }

    @Override
    public String logout(LogoutRequest logoutRequest) {
        Customer user = findCustomer(logoutRequest.getEmail());
        if(!user.getPassword().equals(logoutRequest.getPassword())) throw new InvalidPasswordException("Invalid Detail Provided");
        user.setActive(false);
        customers.save(user);
        return "Logout Successful";
    }

    @Override
    public AddProductResponse searchForProductByName(SearchForProductByNameRequest searchRequest) {
        return  productService.findProductByName(searchRequest);
    }

    @Override
    public List<AddProductResponse> searchForProductByCategory(SearchForProductByCategoryRequest searchRequest) {
        return productService.findProductByCategory(searchRequest.getProductCategory());
    }

    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderRequest placeOrderRequest, CreateAddressRequest createAddressRequest, CreateBillingFormatRequest createBillingFormatRequest) {
       return orderService.placeOrder(placeOrderRequest, createAddressRequest, createBillingFormatRequest);

    }

    @Override
    public void receiverOrder(Order order, long customerId) {
        Customer customer = customers.findById(customerId).orElseThrow(()->new UserNotFoundException("User Not Found"));
        customer.getListOfOrders().add(order);
        customers.save(customer);
    }


}
