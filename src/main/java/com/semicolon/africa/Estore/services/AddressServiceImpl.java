package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.Address;
import com.semicolon.africa.Estore.data.repositories.Addresses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressesServices{
    @Autowired
    private Addresses addresses;
    @Override
    public Address save(Address address) {
       return addresses.save(address);
    }
}
