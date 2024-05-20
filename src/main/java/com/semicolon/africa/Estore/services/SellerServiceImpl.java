package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.Seller;
import com.semicolon.africa.Estore.data.repositories.Sellers;
import com.semicolon.africa.Estore.dtos.request.RegisterSellerRequest;
import com.semicolon.africa.Estore.dtos.response.RegisterSellerResponse;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.semicolon.africa.Estore.utils.Mapper.map;

@Service
public class SellerServiceImpl implements SellerService{
    @Autowired
    private Sellers sellers;
    @Override
    public RegisterSellerResponse registerSeller(RegisterSellerRequest request) {
            Seller seller = sellers.save(map(request));
            return map(seller);
    }

}
