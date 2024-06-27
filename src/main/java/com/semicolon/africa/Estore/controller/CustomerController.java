package com.semicolon.africa.Estore.controller;

import com.semicolon.africa.Estore.dtos.request.*;
import com.semicolon.africa.Estore.dtos.response.*;
import com.semicolon.africa.Estore.services.CustomerService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RequestMapping("api/v2")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    @PostMapping("/sign_up")
    public ResponseEntity<RegisterCustomerResponse> registerCustomer(@RequestBody RegisterCustomerRequest request) throws MessagingException {
        var result = customerService.registerCustomer(request);
        return ResponseEntity.status(CREATED).body(result);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        var result = customerService.login(loginRequest);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/search_product_by_name")
    public ResponseEntity<List<AddProductResponse>> searchForProductByName(@RequestBody SearchForProductByNameRequest searchRequest) {
        var result = customerService.searchForProductByName(searchRequest);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/search_product_by_category")
    public ResponseEntity<List<AddProductResponse>> searchForProductByCategory(@RequestBody SearchForProductByCategoryRequest searchRequest) {
        var result = customerService.searchForProductByCategory(searchRequest);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequest logoutRequest) {
        var result = customerService.logout(logoutRequest);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/place_order")
    public ResponseEntity<PlaceOrderResponse> placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest, @RequestBody CreateAddressRequest createAddressRequest, @RequestBody CreateBillingFormatRequest createBillingFormatRequest) throws MessagingException {
        var result = customerService.placeOrder(placeOrderRequest,createAddressRequest,createBillingFormatRequest);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/add_item_to_cart/{customer_id}")
    public ResponseEntity<AddItemResponse> addItemToCart(@RequestBody AddItemToCartRequest addItemToCartRequest, @PathVariable("customer_id") Long customerId) {
        var result = customerService.addItemToCart(addItemToCartRequest,customerId);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("remove_item_from_cart/{customer_id}")
    public ResponseEntity<String> removeItemFromCart(@RequestBody RemoveItemFromCartRequest removeItemFromCartRequest, @PathVariable("customer_id") Long customerId) {
        var result = customerService.removeItemFromCart(removeItemFromCartRequest,customerId);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/get_list_of_order")
    public ResponseEntity<List<OrderResponse>> getListOfOrders(@PathVariable("customer_id") Long customerId) {
        var result = customerService.getListOfOrders(customerId);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/cancel_order")
    public ResponseEntity<CancelOrderResponse> cancelOrder(@RequestBody CancelOrderRequest cancelOrderRequest) {
        var result = customerService.cancelOrder(cancelOrderRequest);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/payment")
    public ResponseEntity<InitializePaymentResponse> pay(@RequestBody PaymentRequest paymentRequest) {
        var result = customerService.pay(paymentRequest);
        return ResponseEntity.ok(result);
    }


}
