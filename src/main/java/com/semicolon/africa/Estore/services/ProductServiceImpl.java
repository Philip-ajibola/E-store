package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.Product;
import com.semicolon.africa.Estore.data.models.ProductCategory;
import com.semicolon.africa.Estore.data.repositories.Products;
import com.semicolon.africa.Estore.dtos.request.*;
import com.semicolon.africa.Estore.dtos.response.AddProductResponse;
import com.semicolon.africa.Estore.exceptions.InvalidProductNameException;
import com.semicolon.africa.Estore.exceptions.ProductNotFoundException;
import com.semicolon.africa.Estore.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.semicolon.africa.Estore.utils.Mapper.map;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    Products products;

    @Override
    public long count() {
        return products.count();
    }

    @Override
    public AddProductResponse addProduct(AddProductRequest addProduct) {
        validateProductName(addProduct.getProductName());
       Product product = products.save(map(addProduct));
       return map(product);
    }

    @Override
    public String deleteProduct(DeleteProductRequest deleteProductRequest) {
        Product product = findProductById(deleteProductRequest.getProductId());
        products.delete(product);
        return "Product deleted";
    }

    @Override
    public Product findProductBy(long productId) {
        return products.findById(productId).orElseThrow(()-> new ProductNotFoundException("Product Not Found"));
    }

    @Override
    public String editProduct(EditProductRequest editProductReQuest) {
        Product product = findProductById(editProductReQuest.getProductId());
        if(!Objects.equals(editProductReQuest.getProductPrice(), BigDecimal.valueOf(0))) product.setProductPrice(editProductReQuest.getProductPrice());
        if(!editProductReQuest.getProductDescription().isBlank()) product.setProductDescription(editProductReQuest.getProductDescription());
        if(editProductReQuest.getCategory() != ProductCategory.NONE) product.setCategory(editProductReQuest.getCategory());
        if(!editProductReQuest.getProductName().isBlank()) product.setProductName(editProductReQuest.getProductName());
        products.save(product);
        return "Product Edited Successfully";
    }

    @Override
    public void deleteAll() {
        products.deleteAll();
    }

    @Override
    public AddProductResponse findProductByName(SearchForProductByNameRequest searchRequest) {
        Product product = products.findProductByProductName(searchRequest.getProductName());
        if(product == null) throw new ProductNotFoundException("Product Not Found");
        return map(product);
    }

    @Override
    public List<AddProductResponse> findProductByCategory(ProductCategory productCategory) {
        List<Product> productList = products.findProductByCategory(productCategory);
        return productList.stream().map(Mapper::map).collect(Collectors.toList());
    }

    @Override
    public List<Product> findAllProductBy(long sellerId) {
        List<Product> productList = products.findAllProductBySellerId(sellerId);
        if(productList.isEmpty())throw new ProductNotFoundException("No Product Found");
        return productList;
    }


    public Product findProductById(long productId) {
        return products.findById(productId).orElseThrow(()-> new ProductNotFoundException("Product not Found"));
    }

    private void validateProductName(String productName) {
        if(productName == null || productName.isBlank()) throw new InvalidProductNameException("Invalid Product. Product Name must not be empty or null");
    }


}
