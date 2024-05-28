package com.semicolon.africa.Estore.dtos.request;

import com.semicolon.africa.Estore.data.models.City;
import lombok.Data;

@Data
public class CreateAddressRequest {
    private String streetName;
    private City city;
    private String houseNumber;

}
