package com.semicolon.africa.Estore.data.repositories;

import com.semicolon.africa.Estore.data.models.Product;
import com.semicolon.africa.Estore.data.models.ProductCategory;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface Products extends JpaRepository<Product, Long> {
    Product findProductByProductName(@NotNull(message = "ProductName can't be null") String productName);

    List<Product> findProductByCategory(ProductCategory productCategory);

    List<Product> findAllProductBySellerId(long sellerId);
}
