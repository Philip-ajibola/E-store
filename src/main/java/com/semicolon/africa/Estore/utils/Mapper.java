package com.semicolon.africa.Estore.utils;

import com.semicolon.africa.Estore.data.models.Product;
import com.semicolon.africa.Estore.data.models.Seller;
import com.semicolon.africa.Estore.dtos.request.AddProductRequest;
import com.semicolon.africa.Estore.dtos.request.RegisterSellerRequest;
import com.semicolon.africa.Estore.dtos.response.AddProductResponse;
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
}
