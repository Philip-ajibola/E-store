package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.Product;
import com.semicolon.africa.Estore.data.models.Seller;
import com.semicolon.africa.Estore.data.repositories.Sellers;
import com.semicolon.africa.Estore.dtos.request.*;
import com.semicolon.africa.Estore.dtos.response.AddProductResponse;
import com.semicolon.africa.Estore.dtos.response.RegisterSellerResponse;
import com.semicolon.africa.Estore.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.semicolon.africa.Estore.utils.Mapper.map;

@Service
public class SellerServiceImpl implements SellerService{
    @Autowired
    private Sellers sellers;
    @Autowired
    private ProductService productService;
    @Override
    public RegisterSellerResponse registerSeller(RegisterSellerRequest request) {
        validateRequest(request);
        validateExistence(request.getSeller_email(),request.getSeller_name());
        Seller seller = sellers.save(map(request));
            return map(seller);
    }

    @Override
    public String login(LoginRequest loginRequest) {
        Seller seller = findSeller(loginRequest.getEmail());
        if(!seller.getPassword().equals(loginRequest.getPassword())) throw new InvalidPasswordException("Password or gmail not valid");
        seller.setActive(true);
        sellers.save(seller);
        return "Login Successful";
    }

    @Override
    public String logout(LogoutRequest logoutRequest) {
        Seller seller = findSeller(logoutRequest.getEmail());
        if(!seller.getPassword().equals(logoutRequest.getPassword())) throw new InvalidPasswordException("Password or gmail not valid");
        seller.setActive(false);
        sellers.save(seller);
        return "logout Successful";
    }
    @Override
    public  Seller findSeller(String email) {
        Seller seller = sellers.findByEmail(email);
        if (seller == null) throw new SellerNotFoundException("User not found");
        return seller;
    }

    @Override
    public AddProductResponse addProductToStore(AddProductRequest addProduct) {
        return productService.addProduct(addProduct);
    }

    @Override
    public String deleteProduct(DeleteProductRequest deleteProductRequest) {
        return productService.deleteProduct(deleteProductRequest);
    }

    @Override
    public String changeProductPrice(ChangeProductPriceRequest request) {
        return productService.changeProductPrice(request);
    }

    private void validateRequest(RegisterSellerRequest request){
            if(!request.getSeller_name().matches("^[a-zA-Z]+[0-9]*$"))throw new InvalidUserNameException("Username Can Only Contain Alphabet And Number and not null");
            if(request.getPassword().trim().isEmpty())throw new InvalidPasswordException("Provide A Valid Password");
            if(!request.getSeller_email().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) throw new InvalidEmailException("Invalid Email");
    }
    public void validateExistence(String email, String username){
        if(sellers.existsByEmail(email)) throw new EMailExistException("Email Already exist");
    }
}

