package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.Order;
import com.semicolon.africa.Estore.data.models.OrderStatus;
import com.semicolon.africa.Estore.data.models.Product;
import com.semicolon.africa.Estore.data.models.Seller;
import com.semicolon.africa.Estore.data.repositories.Sellers;
import com.semicolon.africa.Estore.dtos.request.*;
import com.semicolon.africa.Estore.dtos.response.AddProductResponse;
import com.semicolon.africa.Estore.dtos.response.RegisterSellerResponse;
import com.semicolon.africa.Estore.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.semicolon.africa.Estore.utils.Mapper.map;

@Service
public class SellerServiceImpl implements SellerService{
    @Autowired
    private Sellers sellers;
    @Autowired
    @Lazy
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @Override
    public List<Product> findAllProductAdded(long sellerId) {
        return productService.findAllProductBy(sellerId);
    }

    @Override
    public Seller findSellerBy(long sellerId) {
        return sellers.findById(sellerId).orElseThrow(()->new SellerNotFoundException("User Not Found"));
    }

    @Override
    public void recieveOrder(Order order, long sellerId) {
        Seller seller = findSellerBy(sellerId);
        seller.getListOfOrders().add(order);
        sellers.save(seller);
    }

    @Override
    public void confirmOrder(long orderId, long sellerId) {
        Seller seller = findSellerBy(sellerId);
        Order order = seller.getListOfOrders().stream().filter(order1 -> order1.getId() == orderId).findFirst().orElseThrow(()->new ItemNotFoundException("Order not found"));
        order.setStatus(OrderStatus.CONFIRMED);
        orderService.saveOrder(order);
    }

    @Override
    public void cancelOrder(long orderId, long sellerId) {
        Seller seller = findSellerBy(sellerId);
        Order order = seller.getListOfOrders().stream().filter(order1 -> order1.getId() == orderId).findFirst().orElseThrow(()->new ItemNotFoundException("Order not found"));
        order.setStatus(OrderStatus.CANCELLED);
        orderService.saveOrder(order);
    }


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
    public String editProduct(EditProductRequest editProductReQuest) {
        return productService.editProduct(editProductReQuest);
    }

    @Override
    public long count() {
        return sellers.count();
    }

    @Override
    public String deactivateAccount(Long sellerId) {
        Seller seller = findSellerBy(sellerId);
        sellers.delete(seller);
        return "Account Deactivated";
    }
    private void checkIfUserIsLogin(Seller user){
        if(!user.isActive()) throw new UserNotLoggedInException("User Not Logged In");
    }

    private Seller findSellerBy(Long sellerId) {
        return sellers.findById(sellerId).orElseThrow(() -> new SellerNotFoundException("Seller Not Found"));
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

