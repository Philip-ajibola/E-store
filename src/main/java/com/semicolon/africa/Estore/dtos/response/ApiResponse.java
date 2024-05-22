package com.semicolon.africa.Estore.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiResponse {
    private boolean success;
    private Object data;
}
