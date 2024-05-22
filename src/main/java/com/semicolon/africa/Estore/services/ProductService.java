package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.Product;
import com.semicolon.africa.Estore.dtos.request.AddProductRequest;
import com.semicolon.africa.Estore.dtos.request.ChangeProductPriceRequest;
import com.semicolon.africa.Estore.dtos.request.DeleteProductRequest;
import com.semicolon.africa.Estore.dtos.response.AddProductResponse;

import java.math.BigDecimal;

public interface ProductService {
    long count();

    AddProductResponse addProduct(AddProductRequest addProduct);

    String deleteProduct(DeleteProductRequest deleteProductRequest);

    Product findProductBy(long productId);

    String changeProductPrice(ChangeProductPriceRequest request);
}
