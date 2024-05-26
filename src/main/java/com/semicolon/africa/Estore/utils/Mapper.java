package com.semicolon.africa.Estore.utils;

import com.semicolon.africa.Estore.data.models.Customer;
import com.semicolon.africa.Estore.data.models.Item;
import com.semicolon.africa.Estore.data.models.Product;
import com.semicolon.africa.Estore.data.models.Seller;
import com.semicolon.africa.Estore.dtos.request.AddItemToCartRequest;
import com.semicolon.africa.Estore.dtos.request.AddProductRequest;
import com.semicolon.africa.Estore.dtos.request.RegisterCustomerRequest;
import com.semicolon.africa.Estore.dtos.request.RegisterSellerRequest;
import com.semicolon.africa.Estore.dtos.response.AddItemResponse;
import com.semicolon.africa.Estore.dtos.response.AddProductResponse;
import com.semicolon.africa.Estore.dtos.response.RegisterCustomerResponse;
import com.semicolon.africa.Estore.dtos.response.RegisterSellerResponse;

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



}
