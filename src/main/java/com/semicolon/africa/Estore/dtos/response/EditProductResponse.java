package com.semicolon.africa.Estore.dtos.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.semicolon.africa.Estore.data.models.ProductCategory;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class EditProductResponse {
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private ProductCategory category;
    private long sellerId;
    private Long id;
    private String url;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateCreates;
}
