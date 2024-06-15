package com.semicolon.africa.Estore.dtos.request;

import com.semicolon.africa.Estore.data.models.ProductCategory;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class AddProductRequest {
    @NotNull(message = "Product Name Can't be null")
    private String productName;
    private String productDescription;
    @NotNull(message = "Please add a picture to the product")
    private MultipartFile file;
    @NotNull(message= "price of product can't be null")
    private BigDecimal productPrice;
    @NotNull(message = "Provide a category")
    private ProductCategory category;
}
