package com.semicolon.africa.Estore.dtos.response;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterSellerResponse {
    private Long id;
    private String seller_name;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateCreated;
}
