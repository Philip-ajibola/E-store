package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.*;
import com.semicolon.africa.Estore.data.repositories.Customers;
import com.semicolon.africa.Estore.dtos.request.*;
import com.semicolon.africa.Estore.dtos.response.*;
import com.semicolon.africa.Estore.exceptions.*;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.semicolon.africa.Estore.utils.HTMLDesigns.sendWelcomeMessage;
import static com.semicolon.africa.Estore.utils.Mapper.map;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private Customers customers;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private MailServices mailServices;
    @Autowired
    @Lazy
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartServices cartServices;
    @Autowired
    private PayStackService payStackService;


    @Override
    public long count() {
        return customers.count();
    }

    @Override
    @Transactional
    public RegisterCustomerResponse registerCustomer(RegisterCustomerRequest request) {
        validateExistingCustomer(request.getCustomerEmail());
        try {
//        if(EmailValidator.getInstance().isValid(request.getCustomerEmail()))throw new InvalidEmailException("Provide A valid email Please");
            Customer customer = map(request);
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
            customer = customers.save(customer);
            Cart cart = new Cart();
            cart.setCustomerId(customer.getId());
            customer.setCart(cart);
            cartServices.save(cart);
            customers.save(customer);
            String subject = "WELCOME TO BOBBY'S STORE";
            mailServices.sendMail(request.getCustomerEmail(), subject, sendWelcomeMessage(request.getCustomerName()));
            return map(customer);
        }catch(MessagingException e){
            throw new PictureUpLoadFialedException(e.getMessage());
        }
    }

    private void validateExistingCustomer(String customerEmail) {
        Customer customer = customers.findByEmail(customerEmail);
        if(customer!= null) throw new InvalidEmailException("Email Already Exist");
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Customer customer = findCustomer(loginRequest.getEmail());
        validatePassword(customer,loginRequest.getPassword());
        customer.setActive(true);
        authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        var jwt = jwtService.generateToken(customer);
        var refreshedToken = jwtService.generateRefreshToken(customer);
        customers.save(customer);
        LoginResponse loginResponse = new LoginResponse(jwt, refreshedToken);
        return loginResponse;
    }

    private void validatePassword(Customer customer,String password) {
        if(!passwordEncoder.matches(password,customer.getPassword()))throw new InvalidPasswordException("Invalid password Or email");
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
    public List<AddProductResponse> searchForProductByName(SearchForProductByNameRequest searchRequest) {
        return  productService.findProductByName(searchRequest);
    }

    @Override
    public List<AddProductResponse> searchForProductByCategory(SearchForProductByCategoryRequest searchRequest) {
        return productService.findProductByCategory(searchRequest.getProductCategory());
    }

    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderRequest placeOrderRequest, CreateAddressRequest createAddressRequest, CreateBillingFormatRequest createBillingFormatRequest) throws MessagingException {
       return orderService.placeOrder(placeOrderRequest, createAddressRequest, createBillingFormatRequest);
    }

    @Override
    public void customerOrders(Order order, Customer customer) {
        customers.save(customer);
    }

    @Override
    public AddItemResponse addItemToCart(AddItemToCartRequest addItemToCartRequest, Long customerId) {
        Customer customer = customers.findById(customerId).orElseThrow(()->new UserNotFoundException("User Not Found:"));
        return cartServices.addItemToCart(addItemToCartRequest, customer);
    }

    @Override
    public String removeItemFromCart(RemoveItemFromCartRequest removeItemFromCartRequest, Long customerId) {
        Customer customer = customers.findById(customerId).orElseThrow(()->new UserNotFoundException("User Not Found:"));
         cartServices.removeItemFromCart(removeItemFromCartRequest, customer);
         return "item removed";
    }

    @Override
    public CancelOrderResponse cancelOrder(CancelOrderRequest cancelOrderRequest) {
         orderService.deleteOrderById(cancelOrderRequest.getOrderId());
        return  new CancelOrderResponse("Order canceled");
    }


    @Override
    public Customer save(Customer customer) {
        return customers.save(customer);
    }

    @Override
    public Customer findCustomerBy(Long customerId) {
        return customers.findById(customerId).orElseThrow(()->new IllegalArgumentException("user not found"));
    }

    @Override
    public List<OrderResponse> getListOfOrders(Long customerId) {
        List<OrderResponse> orders = orderService.getOrderFor(customerId);
        if(orders.isEmpty())throw new OrderNotFoundException("Order Not Found ");
        return orders;
    }

    @Override
    public InitializePaymentResponse pay(PaymentRequest paymentRequest) {
        Order order = orderService.findBy(paymentRequest.getOrderId());
        if(order.getAmount().compareTo(BigDecimal.valueOf(Double.parseDouble(paymentRequest.getAmount())))!=0) throw new InvalidPaymentException("Provide a valid payment, the exact amount");
        InitializePaymentDto initializePaymentDto = new InitializePaymentDto();
        initializePaymentDto.setEmail(paymentRequest.getEmail());
        initializePaymentDto.setAmount(paymentRequest.getAmount());
        initializePaymentDto.setChannels(new String[]{"card"});
        return payStackService.initializePayment(initializePaymentDto);
    }




}
