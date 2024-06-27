package com.semicolon.africa.Estore.services;

import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.semicolon.africa.Estore.data.models.Admin;
import com.semicolon.africa.Estore.data.repositories.Admins;
import com.semicolon.africa.Estore.dtos.request.*;
import com.semicolon.africa.Estore.dtos.response.*;
import com.semicolon.africa.Estore.exceptions.EMailExistException;
import com.semicolon.africa.Estore.exceptions.PictureUpLoadFialedException;
import com.semicolon.africa.Estore.exceptions.UserNotFoundException;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.semicolon.africa.Estore.utils.HTMLDesigns.sendWelcomeMessage;
import static com.semicolon.africa.Estore.utils.Mapper.map;

@Service
@AllArgsConstructor
public class AdminServicesImpl implements AdminServices {
    private ModelMapper mapper;
    private Admins admins;
    private OrderService orderService;
    private ProductService productService;
    private AuthenticationManager authManager;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;
    private MailServices mailServices;

    @Override
    public RegisterAdminResponse register(RegisterAdminRequest request) {
        validateExistence(request.getEmail());
        try {
//        if(EmailValidator.getInstance().isValid(request.getCustomerEmail()))throw new InvalidEmailException("Provide A valid email Please");
            Admin admin = mapper.map(request,Admin.class);
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            admin = admins.save(admin);

            String subject = "WELCOME TO BOBBY'S STORE";
            mailServices.sendMail(request.getEmail(), subject, sendWelcomeMessage(request.getUsername()));
            return mapper.map(admin,RegisterAdminResponse.class);
        }catch(MessagingException e){
            throw new PictureUpLoadFialedException(e.getMessage());
        }
    }

    private void validateExistence(String email) {
        if(admins.existsByEmail(email)) throw new EMailExistException("Email Already exist");
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Admin admin = admins.findByEmail(request.getEmail());
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var jwt = jwtService.generateToken(admin);
        var refreshedToken = jwtService.generateRefreshToken(admin);
        admins.save(admin);
        LoginResponse loginResponse = new LoginResponse(jwt, refreshedToken);
        return loginResponse;
    }

    @Override
    public ApproveOrderResponse approveOrder(ApproveOrderRequest request) {
        OrderResponse orderResponse = orderService.confirmOrder(request.getOrderId());
        return new ApproveOrderResponse(orderResponse);
    }

    @Override
    public AddProductResponse addProductToStore(AddProductRequest addProduct) throws IOException {
        return productService.addProduct(addProduct);
    }

    @Override
    public String deleteProduct(DeleteProductRequest deleteProductRequest) {
        return productService.deleteProduct(deleteProductRequest);
    }

    @Override
    public EditProductResponse editProduct(Long id, JsonPatch patch) throws JsonPatchException {
        return productService.editProduct(id, patch);
    }

    @Override
    public long count() {
        return admins.count();
    }

    @Override
    public String deactivateAccount(Long id) {
        admins.deleteById(id);
        return "Account Deactivated";
    }

    @Override
    public void deleteAll() {
         admins.deleteAll();
    }

    @Override
    public FIndAdminResponse findAdmin(String email) {
        Admin admin=  admins.findByEmail(email);
        if(admin == null)throw new UserNotFoundException("Admin not found");
        return mapper.map(admin, FIndAdminResponse.class);
    }

    @Override
    public List<AddProductResponse> findAllProductAdded() {
        return productService.findAll();
    }

}
