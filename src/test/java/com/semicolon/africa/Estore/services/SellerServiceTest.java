package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.Product;
import com.semicolon.africa.Estore.data.models.ProductCategory;
import com.semicolon.africa.Estore.data.models.Seller;
import com.semicolon.africa.Estore.data.repositories.Sellers;
import com.semicolon.africa.Estore.dtos.request.*;
import com.semicolon.africa.Estore.dtos.response.AddProductResponse;
import com.semicolon.africa.Estore.exceptions.InvalidPasswordException;
import com.semicolon.africa.Estore.exceptions.ProductNotFoundException;
import com.semicolon.africa.Estore.exceptions.SellerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class SellerServiceTest {
    @Autowired
    private Sellers sellers;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private ProductService productService;
    RegisterSellerRequest registerSellerRequest;
    AddProductRequest addProduct;
    LoginRequest loginRequest;
    @BeforeEach
    public void setUp() {
        registerSellerRequest = new RegisterSellerRequest();
        registerSellerRequest.setSeller_name("Sellername");
        registerSellerRequest.setSeller_email("ajibolaphilip10@gmail.com");
        registerSellerRequest.setSeller_phone("SellerPhone");
        registerSellerRequest.setPassword("Seller_password");

        addProduct = new AddProductRequest();
        addProduct.setProductName("productName");
        addProduct.setProductDescription("Product_description");
        addProduct.setProductPrice(BigDecimal.valueOf(2000));
        addProduct.setCategory(ProductCategory.CLOTHING);


        loginRequest = new LoginRequest();
        loginRequest.setEmail(registerSellerRequest.getSeller_email());
        loginRequest.setPassword(registerSellerRequest.getPassword());

        sellers.deleteAll();
        productService.deleteAll();
    }
    @Test
    public void testThatSellerCanRegister(){

        sellerService.registerSeller(registerSellerRequest);
        assertEquals("Sellername",sellers.findByEmail(registerSellerRequest.getSeller_email()).getUsername());
        assertEquals(1,sellerService.count());
    }
    @Test
    public void testThatSellerCanFindSeller(){
        sellerService.registerSeller(registerSellerRequest);
        sellerService.login(loginRequest);
        Seller seller = sellerService.findSeller(registerSellerRequest.getSeller_email());
        assertTrue(seller.isActive());
    }
    @Test
    public void testThatUserCanLogOut(){
        sellerService.registerSeller(registerSellerRequest);
        sellerService.login(loginRequest);
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setEmail(registerSellerRequest.getSeller_email());
        logoutRequest.setPassword(registerSellerRequest.getPassword());
        sellerService.logout(logoutRequest);
        Seller seller = sellerService.findSeller(registerSellerRequest.getSeller_email());
        assertFalse(seller.isActive());
    }
    @Test
    public void testThatWhenSellerTrysToLoginWithInvalidEmail_ExceptionIsThrown(){
        sellerService.registerSeller(registerSellerRequest);
        loginRequest.setEmail("ajibola@gmail.com");
        assertThrows(SellerNotFoundException.class,()->sellerService.login(loginRequest));
    }
    @Test
    public void testThatWhenSellerTrysToLoginWithInvalidName_ExceptionIsThrown(){
        sellerService.registerSeller(registerSellerRequest);
        loginRequest.setPassword("wrong_password");
        assertThrows(InvalidPasswordException.class,()->sellerService.login(loginRequest));
    }
    @Test
    public void testThatUserCanAddProductToTheStore(){
        long id = sellerService.registerSeller(registerSellerRequest).getId();
        sellerService.login(loginRequest);
        addProduct.setSellerId(id);
        sellerService.addProductToStore(addProduct);
        assertEquals(1,productService.count());
    }
    @Test
    public void testThatSellerCanDeleteProductFromTheStore(){
        long id = sellerService.registerSeller(registerSellerRequest).getId();
        sellerService.login(loginRequest);
        addProduct.setSellerId(id);
        AddProductResponse addProductResponse = sellerService.addProductToStore(addProduct);
        assertEquals(1,productService.count());

        DeleteProductRequest deleteProductRequest = new DeleteProductRequest();
        deleteProductRequest.setProductId(addProductResponse.getProductId());
        deleteProductRequest.setSellerId(id);
        sellerService.deleteProduct(deleteProductRequest);
        assertEquals(0,productService.count());
    }
    @Test
    public void testThatProductNameCanBeChanged(){
        sellerService.registerSeller(registerSellerRequest);
        sellerService.login(loginRequest);
        AddProductResponse addProductResponse = sellerService.addProductToStore(addProduct);
        EditProductRequest editProductReQuest = new EditProductRequest();
        editProductReQuest.setProductName("newName");
        editProductReQuest.setProductId(addProductResponse.getProductId());
        sellerService.editProduct(editProductReQuest);
        Product product  = productService.findProductBy(addProductResponse.getProductId());
        assertEquals("newName",product.getProductName());
        assertEquals(BigDecimal.valueOf(2000).setScale(2,RoundingMode.HALF_EVEN),product.getProductPrice());
    }
    @Test
    public  void testThatProductWithInvalidIdCannotBeEdited(){
        sellerService.registerSeller(registerSellerRequest);
        sellerService.login(loginRequest);
        AddProductResponse addProductResponse = sellerService.addProductToStore(addProduct);
        EditProductRequest editProductReQuest = new EditProductRequest();
        editProductReQuest.setProductName("newName");
        editProductReQuest.setProductId(addProductResponse.getProductId()+3);
        Product product = productService.findProductBy(addProductResponse.getProductId());
        assertThrows(ProductNotFoundException.class,()->sellerService.editProduct(editProductReQuest));
        assertThat(product.getProductName(), equalTo(addProductResponse.getProductName()));
    }
    @Test
    public void testThatSellerCanDeActivateAccount(){
        sellerService.registerSeller(registerSellerRequest);
        sellerService.login(loginRequest);
        Seller seller = sellerService.findSeller(registerSellerRequest.getSeller_email());
        sellerService.deactivateAccount(seller.getId());
        assertThat(sellerService.count(),equalTo(0L));
    }
    @Test
    public void testThatUserCanGetAllProductAdded(){
        long sellerId =sellerService.registerSeller(registerSellerRequest).getId();
        sellerService.login(loginRequest);
        addProduct.setSellerId(sellerId);
        sellerService.addProductToStore(addProduct);
        assertThat(sellerService.findAllProductAdded(sellerId).size(), equalTo(1));
    }


}