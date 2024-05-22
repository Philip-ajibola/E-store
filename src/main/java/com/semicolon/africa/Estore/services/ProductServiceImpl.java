package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.Product;
import com.semicolon.africa.Estore.data.repositories.Products;
import com.semicolon.africa.Estore.data.repositories.Sellers;
import com.semicolon.africa.Estore.dtos.request.AddProductRequest;
import com.semicolon.africa.Estore.dtos.request.ChangeProductPriceRequest;
import com.semicolon.africa.Estore.dtos.request.DeleteProductRequest;
import com.semicolon.africa.Estore.dtos.response.AddProductResponse;
import com.semicolon.africa.Estore.exceptions.InvalidProductNameException;
import com.semicolon.africa.Estore.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
    public String changeProductPrice(ChangeProductPriceRequest request) {
        Product product = findProductBy(request.getProductId());
        product.setProductPrice(request.getNewPrice());
        products.save(product);
        return "Price Changed SuccessFully";
    }

    private Product findProductById(long productId) {
        return products.findById(productId).orElseThrow(()-> new ProductNotFoundException("Product not Found"));
    }

    private void validateProductName(String productName) {
        if(productName == null || productName.isBlank()) throw new InvalidProductNameException("Invalid Product. Product Name must not be empty or null");
    }


}
