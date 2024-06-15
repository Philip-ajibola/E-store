package com.semicolon.africa.Estore.services;

import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.semicolon.africa.Estore.data.models.Product;
import com.semicolon.africa.Estore.data.models.ProductCategory;
import com.semicolon.africa.Estore.dtos.request.*;
import com.semicolon.africa.Estore.dtos.response.AddProductResponse;
import com.semicolon.africa.Estore.dtos.response.EditProductResponse;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    long count();

    AddProductResponse addProduct(AddProductRequest addProduct) throws IOException;

    String deleteProduct(DeleteProductRequest deleteProductRequest);

    Product findProductBy(Long productId);

    EditProductResponse editProduct(Long productId, JsonPatch patch) throws JsonPatchException;

    void deleteAll();

    List<AddProductResponse> findProductByName(SearchForProductByNameRequest searchRequest);

    List<AddProductResponse> findProductByCategory(ProductCategory productCategory);

    List<AddProductResponse> findAll();
}
