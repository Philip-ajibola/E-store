package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.Product;
import com.semicolon.africa.Estore.data.models.ProductCategory;
import com.semicolon.africa.Estore.dtos.request.*;
import com.semicolon.africa.Estore.dtos.response.AddProductResponse;

import java.util.List;

public interface ProductService {
    long count();

    AddProductResponse addProduct(AddProductRequest addProduct);

    String deleteProduct(DeleteProductRequest deleteProductRequest);

    Product findProductBy(long productId);

    String editProduct(EditProductRequest editProductReQuest);

    void deleteAll();

    AddProductResponse findProductByName(SearchForProductByNameRequest searchRequest);

    List<AddProductResponse> findProductByCategory(ProductCategory productCategory);

    List<Product> findAllProductBy(long sellerId);
}
