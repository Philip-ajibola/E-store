package com.semicolon.africa.Estore.data.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Product {
    @NotNull(message = "ProductName can't be null")
    private String productName;
    private String productDescription;
    private String url;
    @NotNull(message = "Provide a price for the product")
    private BigDecimal productPrice = new BigDecimal("0.0");
    @NotNull(message = "Provide a category")
    private ProductCategory category;
//    @JoinColumn
//    private long sellerId;
    @Id
    @GeneratedValue
    private Long id;
    @Setter(AccessLevel.NONE)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateCreated;

    @PrePersist
    public void setDateCreated(){
        this.dateCreated = LocalDateTime.now();
    }

}
