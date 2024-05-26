package com.semicolon.africa.Estore.dtos.request;

import com.semicolon.africa.Estore.data.models.ProductCategory;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EditProductRequest {
    private long productId;
    private String productName = "";
    private String productDescription = "";
    private ProductCategory category = ProductCategory.NONE;
    private BigDecimal productPrice = BigDecimal.ZERO;
}
