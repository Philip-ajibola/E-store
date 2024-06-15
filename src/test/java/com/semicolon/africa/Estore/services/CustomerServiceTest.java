package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.*;
import com.semicolon.africa.Estore.data.repositories.Customers;
import com.semicolon.africa.Estore.dtos.request.*;
import com.semicolon.africa.Estore.dtos.response.AddItemResponse;
import com.semicolon.africa.Estore.dtos.response.PlaceOrderResponse;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {
   @Autowired
   private CustomerService customerService;
   @Autowired
   private OrderService orderService;
   @Autowired
   private Customers customers;
   @Autowired
   private AdminServices adminServices;
   @Autowired
   private ProductService productService;
   @Autowired
   private CartServices cartServices;
   @Autowired
   private ItemService itemService;
   private RegisterAdminRequest registerAdminRequest;



   private RegisterCustomerRequest request;

   private LoginRequest loginRequest;
   private long sellerId;


   @AfterEach
   public void tearDown() {
       customers.deleteAll();
       adminServices.deleteAll();
       productService.deleteAll();
   }
    @BeforeEach
   public void setUp() throws MessagingException {
       request = new RegisterCustomerRequest();
       request.setCustomerEmail("ajibolaphilip@gmail.com");
       request.setCustomerName("CustomerName");
       request.setCustomerPhone("09027531222");
       request.setPassword("Customer_password");
       request.setRole(Role.CUSTOMER);

         registerAdminRequest = new RegisterAdminRequest();
        registerAdminRequest.setUsername("Sellername");
        registerAdminRequest.setEmail("ajibolaphilip10@gmail.com");
        registerAdminRequest.setPhoneNumber("09027531222");
        registerAdminRequest.setPassword("Seller_password");
        registerAdminRequest.setRole(Role.ADMIN);

        sellerId = adminServices.register(registerAdminRequest).getId();

//         loginRequest = new LoginRequest();
//        loginRequest.setEmail(request.getCustomerEmail());
//        loginRequest.setPassword(request.getPassword());

   }

   @Test
   public void testThatCustomerCanCanRegister() throws MessagingException {
       customerService.registerCustomer(request);
       assertThat(customerService.count(), equalTo(1L));
       customers.delete(customerService.findCustomer(request.getCustomerEmail()));
   }
//   @Test
//   public void testThatUserCanLogin(){
//       customerService.registerCustomer(request);
//
//     //  customerService.login(loginRequest);
//
//       Customer customer = customerService.findCustomer(loginRequest.getEmail());
//       assertThat(customer.isActive(), equalTo(true));
//   }
//   @Test
//   public void testThatUserCanLogout(){
//       customerService.registerCustomer(request);
//
//       LogoutRequest loginRequest = new LogoutRequest();
//        loginRequest.setEmail(request.getCustomerEmail());
//        loginRequest.setPassword(request.getPassword());
//        customerService.logout(loginRequest);
//
//        Customer customer = customerService.findCustomer(loginRequest.getEmail());
//        assertFalse(customer.isActive());
//   }
   @Test
    public  void testThatCustomerCanSearchForProductByName() throws IOException, MessagingException {
        customerService.registerCustomer(request);
        Customer customer = customerService.findCustomer(request.getCustomerEmail());
        SearchForProductByNameRequest searchRequest = new SearchForProductByNameRequest();

        AddProductRequest addRequest = new AddProductRequest();
        addRequest.setProductName("productName");
        addRequest.setProductDescription("Product_description");
        addRequest.setProductPrice(BigDecimal.valueOf(2000));
        addRequest.setCategory(ProductCategory.CLOTHING);

       Product product = productService.findProductBy(adminServices.addProductToStore(addRequest).getProductId());
        searchRequest.setProductName("productName");
        assertEquals(1,customerService.searchForProductByName(searchRequest).size());
       customers.delete(customerService.findCustomer(request.getCustomerEmail()));

   }
   @Test
    public void testThatCustomerCanFIndProductByCategory() throws IOException, MessagingException {
        customerService.registerCustomer(request);
        Customer customer = customerService.findCustomer(request.getCustomerEmail());
        SearchForProductByCategoryRequest searchRequest = new SearchForProductByCategoryRequest();

        AddProductRequest addRequest = new AddProductRequest();
        addRequest.setProductName("productName");
        addRequest.setProductDescription("Product_description");
        addRequest.setProductPrice(BigDecimal.valueOf(2000));
        addRequest.setCategory(ProductCategory.CLOTHING);

        Product product = productService.findProductBy(adminServices.addProductToStore(addRequest).getProductId());
        searchRequest.setProductCategory(ProductCategory.CLOTHING);
        assertEquals(1,customerService.searchForProductByCategory(searchRequest).size());
       customers.delete(customerService.findCustomer(request.getCustomerEmail()));
   }

   @Test
    public void testThatCustomerCanAddItemToCart() throws IOException, MessagingException {
        customerService.registerCustomer(request);
        Customer customer = customerService.findCustomer(request.getCustomerEmail());
        AddProductRequest addRequest = new AddProductRequest();
        addRequest.setProductName("productName");
        addRequest.setProductDescription("Product_description");
        addRequest.setProductPrice(BigDecimal.valueOf(2000));
        addRequest.setCategory(ProductCategory.CLOTHING);

       SearchForProductByNameRequest searchRequest = new SearchForProductByNameRequest();
        searchRequest.setProductName(addRequest.getProductName());

       Product product = productService.findProductBy(adminServices.addProductToStore(addRequest).getProductId());
       AddItemToCartRequest addItemToCartRequest = new AddItemToCartRequest();
       addItemToCartRequest.setProductId(product.getId());
       addItemToCartRequest.setCustomerId(customer.getId());
       addItemToCartRequest.setQuantity(2);

       cartServices.addItemToCart(addItemToCartRequest,customer);
       assertEquals(1, cartServices.countCustomerItem(customer.getId()));

   }
   @Test
    public void testThatCustomerCanAddMoreItemToCart() throws IOException, MessagingException {
        customerService.registerCustomer(request);
       Customer customer = customerService.findCustomer(request.getCustomerEmail());


       AddProductRequest addRequest = new AddProductRequest();
       addRequest.setProductName("productName");
       addRequest.setProductDescription("Product_description");
       addRequest.setProductPrice(BigDecimal.valueOf(2000));
       addRequest.setCategory(ProductCategory.CLOTHING);

       AddProductRequest addRequest1 = new AddProductRequest();
       addRequest1.setProductName("productName");
       addRequest1.setProductDescription("Product_description");
       addRequest1.setProductPrice(BigDecimal.valueOf(2000));
       addRequest1.setCategory(ProductCategory.CLOTHING);

       SearchForProductByNameRequest searchRequest = new SearchForProductByNameRequest();
       searchRequest.setProductName(addRequest.getProductName());

       Product product = productService.findProductBy(adminServices.addProductToStore(addRequest).getProductId());
       Product product1 = productService.findProductBy(adminServices.addProductToStore(addRequest1).getProductId());

       AddItemToCartRequest addItemToCartRequest = new AddItemToCartRequest();
       addItemToCartRequest.setProductId(product.getId());
       addItemToCartRequest.setCustomerId(customer.getId());
       addItemToCartRequest.setQuantity(2);

       AddItemToCartRequest addItemToCartRequest1 = new AddItemToCartRequest();
       addItemToCartRequest1.setProductId(product1.getId());
       addItemToCartRequest1.setCustomerId(customer.getId());
       addItemToCartRequest1.setQuantity(3);

       cartServices.addItemToCart(addItemToCartRequest,customer);
       cartServices.addItemToCart(addItemToCartRequest1,customer);

       assertEquals(2,cartServices.countCustomerItem(customer.getId()));
       customers.delete(customerService.findCustomer(request.getCustomerEmail()));
   }
   @Test public void testThatCustomerCanRemoveFromCart() throws IOException, MessagingException {
       customerService.registerCustomer(request);
       Customer customer = customerService.findCustomer(request.getCustomerEmail());


       AddProductRequest addRequest = new AddProductRequest();
       addRequest.setProductName("productName");
       addRequest.setProductDescription("Product_description");
       addRequest.setProductPrice(BigDecimal.valueOf(2000));
       addRequest.setCategory(ProductCategory.CLOTHING);

       AddProductRequest addRequest1 = new AddProductRequest();
       addRequest1.setProductName("productName");
       addRequest1.setProductDescription("Product_description");
       addRequest1.setProductPrice(BigDecimal.valueOf(2000));
       addRequest1.setCategory(ProductCategory.CLOTHING);

       SearchForProductByNameRequest searchRequest = new SearchForProductByNameRequest();
       searchRequest.setProductName(addRequest.getProductName());

       Product product = productService.findProductBy(adminServices.addProductToStore(addRequest).getProductId());
       Product product1 = productService.findProductBy(adminServices.addProductToStore(addRequest1).getProductId());

       AddItemToCartRequest addItemToCartRequest = new AddItemToCartRequest();
       addItemToCartRequest.setProductId(product.getId());
       addItemToCartRequest.setCustomerId(customer.getId());
       addItemToCartRequest.setQuantity(2);

       AddItemToCartRequest addItemToCartRequest1 = new AddItemToCartRequest();
       addItemToCartRequest1.setProductId(product1.getId());
       addItemToCartRequest1.setCustomerId(customer.getId());
       addItemToCartRequest1.setQuantity(3);

       AddItemResponse addItemResponse =cartServices.addItemToCart(addItemToCartRequest,customer);
       customerService.addItemToCart(addItemToCartRequest1,customer.getId());

       RemoveItemFromCartRequest removeItemFromCartRequest = new RemoveItemFromCartRequest();
       removeItemFromCartRequest.setItemId(addItemResponse.getItemId());
       cartServices.removeItemFromCart(removeItemFromCartRequest,customer);

       assertEquals(1,cartServices.countCustomerItem(customer.getId()));
       customers.delete(customerService.findCustomer(request.getCustomerEmail()));
   }
   @Test
    public void testCustomerCanViewCart() throws IOException, MessagingException {
       customerService.registerCustomer(request);
       Customer customer = customerService.findCustomer(request.getCustomerEmail());


       AddProductRequest addRequest = new AddProductRequest();
       addRequest.setProductName("productName");
       addRequest.setProductDescription("Product_description");
       addRequest.setProductPrice(BigDecimal.valueOf(2000));
       addRequest.setCategory(ProductCategory.CLOTHING);

       AddProductRequest addRequest1 = new AddProductRequest();
       addRequest1.setProductName("productName");
       addRequest1.setProductDescription("Product_description");
       addRequest1.setProductPrice(BigDecimal.valueOf(2000));
       addRequest1.setCategory(ProductCategory.CLOTHING);

       SearchForProductByNameRequest searchRequest = new SearchForProductByNameRequest();
       searchRequest.setProductName(addRequest.getProductName());

       Product product = productService.findProductBy(adminServices.addProductToStore(addRequest).getProductId());
       Product product1 = productService.findProductBy(adminServices.addProductToStore(addRequest1).getProductId());

       AddItemToCartRequest addItemToCartRequest = new AddItemToCartRequest();
       addItemToCartRequest.setProductId(product.getId());
       addItemToCartRequest.setCustomerId(customer.getId());
       addItemToCartRequest.setQuantity(2);

       AddItemToCartRequest addItemToCartRequest1 = new AddItemToCartRequest();
       addItemToCartRequest1.setProductId(product1.getId());
       addItemToCartRequest1.setCustomerId(customer.getId());
       addItemToCartRequest1.setQuantity(3);

       customerService.addItemToCart(addItemToCartRequest,customer.getId());
       customerService.addItemToCart(addItemToCartRequest1,customer.getId());

       assertEquals(2,cartServices.viewCart(customer.getId()).size());

       customers.delete(customerService.findCustomer(request.getCustomerEmail()));
   }

   @Test
    public void testThatCustomerCanPlaceOrder() throws IOException, MessagingException {
       customerService.registerCustomer(request);
       //sellerId =sellerService.registerSeller(registerSellerRequest).getId();

       Customer customer = customerService.findCustomer(request.getCustomerEmail());

       AddProductRequest addRequest = new AddProductRequest();
       addRequest.setProductName("productName");
       addRequest.setProductDescription("Product_description");
       addRequest.setProductPrice(BigDecimal.valueOf(2000));
       addRequest.setCategory(ProductCategory.CLOTHING);

       AddProductRequest addRequest1 = new AddProductRequest();
       addRequest1.setProductName("productName");
       addRequest1.setProductDescription("Product_description");
       addRequest1.setProductPrice(BigDecimal.valueOf(2000));
       addRequest1.setCategory(ProductCategory.CLOTHING);

       SearchForProductByNameRequest searchRequest = new SearchForProductByNameRequest();
       searchRequest.setProductName(addRequest.getProductName());

       Product product = productService.findProductBy(adminServices.addProductToStore(addRequest).getProductId());
       Product product1 = productService.findProductBy(adminServices.addProductToStore(addRequest1).getProductId());

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


       customerService.placeOrder(placeOrderRequest,createAddressRequest,createBillingFormatRequest);

       assertEquals(1,orderService.countOrderFor(customer.getId()));
       customer = customerService.findCustomer(request.getCustomerEmail());
       assertEquals(1,customerService.getListOfOrders(customer.getId()).size());
       customers.delete(customerService.findCustomer(request.getCustomerEmail()));
   }

   @Test
    public void testThatCustomerCanCancelOrder() throws MessagingException, IOException {
       customerService.registerCustomer(request);
       //sellerId =sellerService.registerSeller(registerSellerRequest).getId();
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
       RemoveItemFromCartRequest.CancelOrderRequest cancelOrderRequest = new RemoveItemFromCartRequest.CancelOrderRequest();
        cancelOrderRequest.setCustomerId(customer.getId());
        cancelOrderRequest.setOrderId(response.getOrderId());
       System.out.println(response.getOrderId());
        customerService.cancelOrder(cancelOrderRequest);

        assertEquals(0,customerService.getOrder(customer.getId()).size());
   }

}