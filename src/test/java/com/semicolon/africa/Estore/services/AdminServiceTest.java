package com.semicolon.africa.Estore.services;

import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import com.semicolon.africa.Estore.data.models.*;
import com.semicolon.africa.Estore.data.repositories.Admins;
import com.semicolon.africa.Estore.dtos.request.*;
import com.semicolon.africa.Estore.dtos.response.AddItemResponse;
import com.semicolon.africa.Estore.dtos.response.AddProductResponse;
import com.semicolon.africa.Estore.dtos.response.FIndAdminResponse;
import com.semicolon.africa.Estore.dtos.response.PlaceOrderResponse;
import com.semicolon.africa.Estore.exceptions.InvalidPasswordException;
import com.semicolon.africa.Estore.exceptions.ProductNotFoundException;
import com.semicolon.africa.Estore.exceptions.SellerNotFoundException;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class AdminServiceTest {
    @Autowired
    private Admins admins;
    @Autowired
    private AdminServices adminServices;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CartServices cartServices;
    @Autowired
    private ProductService productService;
    RegisterAdminRequest registerAdminRequest;
    AddProductRequest addProduct;
    LoginRequest loginRequest;
    RegisterCustomerRequest request;
    @BeforeEach
    public void setUp()  {
        registerAdminRequest = new RegisterAdminRequest();
        Path path = Paths.get("C:\\Users\\Dell\\Desktop\\marverickshub\\src\\main\\resources\\static\\thanos.jpeg");

        registerAdminRequest.setUsername("admin");
        registerAdminRequest.setEmail("adedejiadeyemi627@gmail.com");
        registerAdminRequest.setPhoneNumber("SellerPhone");
        registerAdminRequest.setPassword("Seller_password");

        request = new RegisterCustomerRequest();
        request.setCustomerEmail("ajibolaphilip@gmail.com");
        request.setCustomerName("CustomerName");
        request.setCustomerPhone("09027531222");
        request.setPassword("Customer_password");
        request.setRole(Role.CUSTOMER);

        try( var inputStream = Files.newInputStream(path)){
            addProduct = new AddProductRequest();
            addProduct.setProductName("productName");
            addProduct.setProductDescription("Product_description");
            addProduct.setProductPrice(BigDecimal.valueOf(2000));
            addProduct.setCategory(ProductCategory.CLOTHING);
            MockMultipartFile file = new MockMultipartFile("image",inputStream);
            addProduct.setFile(file);
        }catch(IOException exception){
            System.err.println(exception.getMessage());
        }



        loginRequest = new LoginRequest();
        loginRequest.setEmail(registerAdminRequest.getEmail());
        loginRequest.setPassword(registerAdminRequest.getPassword());


    }
    @AfterEach
    public void deleteAll(){
        productService.deleteAll();
    }
    @Test
    public void testThatSellerCanRegister() throws MessagingException {

        adminServices.register(registerAdminRequest);
        assertEquals("adedejiadeyemi627@gmail.com",admins.findByEmail(registerAdminRequest.getEmail()).getUsername());
        assertEquals(1, adminServices.count());
    }
    @Test
    public void testThatSellerCanFindSeller() throws MessagingException {
        adminServices.register(registerAdminRequest);
        FIndAdminResponse admin = adminServices.findAdmin(registerAdminRequest.getEmail());
        assertNotNull(admin);
    }

    @Test
    public void testThatWhenSellerTrysToLoginWithInvalidEmail_ExceptionIsThrown() throws MessagingException {
        adminServices.register(registerAdminRequest);
        loginRequest.setEmail("ajibola@gmail.com");
        assertThrows(SellerNotFoundException.class,()-> adminServices.login(loginRequest));
    }
    @Test
    public void testThatWhenSellerTrysToLoginWithInvalidName_ExceptionIsThrown() throws MessagingException {
        adminServices.register(registerAdminRequest);
        loginRequest.setPassword("wrong_password");
        assertThrows(InvalidPasswordException.class,()-> adminServices.login(loginRequest));
    }
    @Test
    public void testThatUserCanAddProductToTheStore() throws IOException, MessagingException {
         adminServices.register(registerAdminRequest);
        adminServices.addProductToStore(addProduct);
        assertEquals(1,productService.count());
    }
    @Test
    public void testThatSellerCanDeleteProductFromTheStore() throws IOException, MessagingException {
         adminServices.register(registerAdminRequest);

        AddProductResponse addProductResponse = adminServices.addProductToStore(addProduct);
        assertEquals(1,productService.count());

        DeleteProductRequest deleteProductRequest = new DeleteProductRequest();
        deleteProductRequest.setProductId(addProductResponse.getProductId());
        adminServices.deleteProduct(deleteProductRequest);
        assertEquals(0,productService.count());
    }
    @Test
    public void testThatProductNameCanBeChanged() throws JsonPointerException, JsonPatchException, IOException, MessagingException {
        adminServices.register(registerAdminRequest);
        AddProductResponse addProductResponse = adminServices.addProductToStore(addProduct);
        List<JsonPatchOperation> operations = List.of(
                new ReplaceOperation(new JsonPointer("/productName"),new TextNode("newName"))
        );
        adminServices.editProduct(addProductResponse.getProductId(),new JsonPatch(operations));
        Product product  = productService.findProductBy(addProductResponse.getProductId());
        assertEquals("newName",product.getProductName());
        assertEquals(BigDecimal.valueOf(2000).setScale(2,RoundingMode.HALF_EVEN),product.getProductPrice());
    }
    @Test
    public void testThatProductPriceCanBeEdited() throws JsonPointerException, JsonPatchException, IOException, MessagingException {
        adminServices.register(registerAdminRequest);
        AddProductResponse addProductResponse = adminServices.addProductToStore(addProduct);
        List<JsonPatchOperation> operations = List.of(
                new ReplaceOperation(new JsonPointer("/productName"),new TextNode("newProductName"))
        );
        adminServices.editProduct(addProductResponse.getProductId(),new JsonPatch(operations));
        Product product  = productService.findProductBy(addProductResponse.getProductId());
        assertEquals("newProductName",product.getProductName());
        assertEquals(BigDecimal.valueOf(2000).setScale(2,RoundingMode.HALF_EVEN),product.getProductPrice());
    }
    @Test
    public  void testThatProductWithInvalidIdCannotBeEdited() throws JsonPointerException, JsonPatchException, IOException, MessagingException {
        adminServices.register(registerAdminRequest);
        AddProductResponse addProductResponse = adminServices.addProductToStore(addProduct);
        List<JsonPatchOperation> operations = List.of(
                new ReplaceOperation(new JsonPointer("/productPrice"),new TextNode("5000"))
        );
        assertThrows(ProductNotFoundException.class,()-> adminServices.editProduct(20L,new JsonPatch(operations)));
    }
    @Test
    public void testThatSellerCanDeActivateAccount() throws MessagingException {
        adminServices.register(registerAdminRequest);
        FIndAdminResponse admin = adminServices.findAdmin(registerAdminRequest.getEmail());
        adminServices.deactivateAccount(admin.getId());
        assertThat(adminServices.count(),equalTo(0L));
    }
    @Test
    public void testThatUserCanGetAllProductAdded() throws IOException, MessagingException {
        long sellerId = adminServices.register(registerAdminRequest).getId();
        adminServices.addProductToStore(addProduct);
        assertThat(adminServices.findAllProductAdded().size(), equalTo(1));
    }
    @Test
    public void testThatSellerCanConfirmOrder() throws MessagingException, IOException {
        customerService.registerCustomer(request);
        long sellerId = adminServices.register(registerAdminRequest).getId();
        System.out.println(sellerId);

        Customer customer = customerService.findCustomer(request.getCustomerEmail());

        Path path = Paths.get("C:\\Users\\Dell\\Desktop\\marverickshub\\src\\main\\resources\\static\\thanos.jpeg");
        AddProductRequest addProduct1 = new AddProductRequest();
        try( var inputStream = Files.newInputStream(path)){
            addProduct1 = new AddProductRequest();
            addProduct1.setProductName("productName");
            addProduct1.setProductDescription("Product_description");
            addProduct1.setProductPrice(BigDecimal.valueOf(2000));
            addProduct1.setCategory(ProductCategory.CLOTHING);
            MockMultipartFile file = new MockMultipartFile("image",inputStream);
            addProduct1.setFile(file);
        }catch(IOException exception){
            System.err.println(exception.getMessage());
        }
        AddProductRequest addProduct = new AddProductRequest();
        try( var inputStream = Files.newInputStream(path)){
            addProduct = new AddProductRequest();
            addProduct.setProductName("productName");
            addProduct.setProductDescription("Product_description");
            addProduct.setProductPrice(BigDecimal.valueOf(2000));
            addProduct.setCategory(ProductCategory.CLOTHING);
            MockMultipartFile file = new MockMultipartFile("image",inputStream);
            addProduct.setFile(file);
        }catch(IOException exception){
            System.err.println(exception.getMessage());
        }

        SearchForProductByNameRequest searchRequest = new SearchForProductByNameRequest();
        searchRequest.setProductName(addProduct1.getProductName());

        Product product = productService.findProductBy(adminServices.addProductToStore(addProduct1).getProductId());
        Product product1 = productService.findProductBy(adminServices.addProductToStore(addProduct).getProductId());

        AddItemToCartRequest addItemToCartRequest = new AddItemToCartRequest();
        addItemToCartRequest.setProductId(product.getId());
        addItemToCartRequest.setCustomerId(customer.getId());
        addItemToCartRequest.setQuantity(2);

        AddItemToCartRequest addItemToCartRequest1 = new AddItemToCartRequest();
        addItemToCartRequest1.setProductId(product1.getId());
        addItemToCartRequest1.setCustomerId(customer.getId());
        addItemToCartRequest1.setQuantity(3);

        AddItemResponse addItemResponse =cartServices.addItemToCart(addItemToCartRequest,customer);
        cartServices.addItemToCart(addItemToCartRequest1,customer);
        Cart cart = cartServices.findCart(addItemResponse.getCartId());

        CreateBillingFormatRequest createBillingFormatRequest = new CreateBillingFormatRequest();
        createBillingFormatRequest.setReceiversName("receivers_name");
        createBillingFormatRequest.setReceiversActiveContact("09027531222");

        CreateAddressRequest createAddressRequest = new CreateAddressRequest();
        createAddressRequest.setCity(City.IKEJA);
        createAddressRequest.setStreetName("street_name");
        createAddressRequest.setHouseNumber("N0 24");


        PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest();
        placeOrderRequest.setCustomerId(customer.getId());
        placeOrderRequest.setCartId(cart.getId());

        PlaceOrderResponse response =customerService.placeOrder(placeOrderRequest,createAddressRequest,createBillingFormatRequest);
    }


}