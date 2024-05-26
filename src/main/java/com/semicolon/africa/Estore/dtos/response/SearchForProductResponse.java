package com.semicolon.africa.Estore.dtos.response;

import lombok.Data;

@Data
public class SearchForProductResponse {
    private String productName;
    private String productDescription;
    private long productId;

}
