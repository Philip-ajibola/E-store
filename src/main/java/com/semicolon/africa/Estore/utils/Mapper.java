package com.semicolon.africa.Estore.utils;

import com.semicolon.africa.Estore.data.models.*;
import com.semicolon.africa.Estore.dtos.request.*;
import com.semicolon.africa.Estore.dtos.response.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Mapper {
    public  static RegisterSellerResponse map(Seller seller) {
        RegisterSellerResponse registerSellerResponse = new RegisterSellerResponse();
        registerSellerResponse.setSeller_name(seller.getUsername());
        registerSellerResponse.setId(seller.getId());
        return registerSellerResponse;
    }

    public static Seller map(RegisterSellerRequest request) {
        Seller seller = new Seller();
        seller.setEmail(request.getSeller_email());
        seller.setUsername(request.getSeller_name());
        seller.setPhone(request.getSeller_phone());
        seller.setPassword(request.getPassword());
        return seller;
    }
    public static Product map(AddProductRequest addProduct) {
        Product product = new Product();
        product.setProductDescription(addProduct.getProductDescription());
        product.setProductName(addProduct.getProductName());
        product.setSellerId(addProduct.getSellerId());
        product.setProductPrice(addProduct.getProductPrice().setScale(2, RoundingMode.HALF_EVEN));
        product.setCategory(addProduct.getCategory());
        return product;
    }
    public static AddProductResponse map(Product product) {
        AddProductResponse addProductResponse = new AddProductResponse();
        addProductResponse.setProductDescription(product.getProductDescription());
        addProductResponse.setProductName(product.getProductName());
        addProductResponse.setProductId(product.getId());
        return addProductResponse;
    }
    public static Customer map(RegisterCustomerRequest request) {
        Customer customer = new Customer();
        customer.setPassword(request.getPassword());
        customer.setUsername(request.getCustomer_name());
        customer.setEmail(request.getCustomer_email());
        customer.setPhone(request.getCustomer_phone());
        return customer;
    }
    public static RegisterCustomerResponse map(Customer customer) {
        RegisterCustomerResponse registerCustomerResponse = new RegisterCustomerResponse();
        registerCustomerResponse.setSeller_name(customer.getUsername());
        registerCustomerResponse.setId(customer.getId());
        return registerCustomerResponse;
    }
    public static Item map(AddItemToCartRequest addItemToCartRequest) {
        Item item = new Item();
        item.setProductId(addItemToCartRequest.getProductId());
        item.setCustomerId(addItemToCartRequest.getCustomerId());
        item.setQuantity(addItemToCartRequest.getQuantity());
        return item;
    }
    public static AddItemResponse map(Product product, Item item) {
        AddItemResponse addItemResponse = new AddItemResponse();
        addItemResponse.setProductName(product.getProductName());
        addItemResponse.setItemId(item.getId());
        addItemResponse.setQuantity(item.getQuantity());
        addItemResponse.setPrice(product.getProductPrice());
        return addItemResponse;
    }
    public static Item map(long productId, int quantity) {
        Item item = new Item();
        item.setProductId(productId);
        item.setQuantity(quantity);
        return item;
    }
    public static Order map(PlaceOrderRequest placeOrderRequest, Item item, BillingInformation billingInformation, BigDecimal amount) {
        Order order = new Order();
        order.setCustomerId(placeOrderRequest.getCustomerId());
        order.setItem(item);
        order.setBillingInformation(billingInformation);
        order.setAmount(amount);
        order.setStatus(OrderStatus.PENDING);
        return order;
    }

    public static Address map(CreateAddressRequest createAddressRequest) {
        Address address = new Address();
        address.setCity(createAddressRequest.getCity());
        address.setStreet(createAddressRequest.getStreetName());
        address.setHouseNumber(createAddressRequest.getHouseNumber());
        return address;
    }

    public static BillingInformation map(CreateBillingFormatRequest createBillingFormatRequest, Address address) {
        BillingInformation billingInformation = new BillingInformation();
        billingInformation.setReceiverPhoneNumber(createBillingFormatRequest.getReceiversActiveContact());
        billingInformation.setReceiversName(createBillingFormatRequest.getReceiversName());
        billingInformation.setDeliveryAddress(address);
        return billingInformation;
    }

    public  static PlaceOrderResponse map(Order order) {
        PlaceOrderResponse placeOrderResponse = new PlaceOrderResponse();
        placeOrderResponse.setOrderId(order.getId());
        placeOrderResponse.setStatus(order.getStatus());
        return placeOrderResponse;
    }



}
