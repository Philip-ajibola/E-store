package com.semicolon.africa.Estore.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddProductResponse {
    private String productName;
    private String productDescription;
    private long productId;
}
