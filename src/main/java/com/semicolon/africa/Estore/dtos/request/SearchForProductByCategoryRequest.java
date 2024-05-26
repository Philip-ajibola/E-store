package com.semicolon.africa.Estore.dtos.request;

import com.semicolon.africa.Estore.data.models.ProductCategory;
import lombok.Data;

@Data
public class SearchForProductByCategoryRequest {
    private ProductCategory productCategory;
}
