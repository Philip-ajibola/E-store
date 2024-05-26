package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.Customer;
import com.semicolon.africa.Estore.data.models.Product;
import com.semicolon.africa.Estore.data.models.ProductCategory;
import com.semicolon.africa.Estore.dtos.request.*;
import com.semicolon.africa.Estore.dtos.response.AddItemResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {
   @Autowired
   private CustomerService customerService;
   @Autowired
   private SellerService sellerService;
   @Autowired
   private ProductService productService;
   @Autowired
   private CartServices cartServices;
   @Autowired
   private ItemService itemService;



   private RegisterCustomerRequest request;

   private LoginRequest loginRequest;
   private long sellerId;


    @BeforeEach
   public void setUp()  {
       request = new RegisterCustomerRequest();
       request.setCustomer_email("ajibolaphilip10@gmail.com");
       request.setCustomer_name("CustomerName");
       request.setCustomer_phone("CustomerPhone");
       request.setPassword("Customer_password");

        RegisterSellerRequest registerSellerRequest = new RegisterSellerRequest();
        registerSellerRequest.setSeller_name("Sellername");
        registerSellerRequest.setSeller_email("ajibolaphilip10@gmail.com");
        registerSellerRequest.setSeller_phone("SellerPhone");
        registerSellerRequest.setPassword("Seller_password");

        sellerId =sellerService.registerSeller(registerSellerRequest).getId();

         loginRequest = new LoginRequest();
        loginRequest.setEmail(request.getCustomer_email());
        loginRequest.setPassword(request.getPassword());
   }

   @Test
   public void testThatCustomerCanCanRegister(){
       customerService.registerCustomer(request);
       assertThat(customerService.count(), equalTo(1L));
   }
   @Test
   public void testThatUserCanLogin(){
       customerService.registerCustomer(request);

       customerService.login(loginRequest);

       Customer customer = customerService.findCustomer(loginRequest.getEmail());
       assertThat(customer.isActive(), equalTo(true));
   }
   @Test
   public void testThatUserCanLogout(){
       customerService.registerCustomer(request);

       LogoutRequest loginRequest = new LogoutRequest();
        loginRequest.setEmail(request.getCustomer_email());
        loginRequest.setPassword(request.getPassword());
        customerService.logout(loginRequest);

        Customer customer = customerService.findCustomer(loginRequest.getEmail());
        assertFalse(customer.isActive());
   }
   @Test
    public  void testThatCustomerCanSearchForProductByName(){
        customerService.registerCustomer(request);
        Customer customer = customerService.findCustomer(loginRequest.getEmail());
        SearchForProductByNameRequest searchRequest = new SearchForProductByNameRequest();

        AddProductRequest addRequest = new AddProductRequest();
        addRequest.setProductName("productName");
        addRequest.setProductDescription("Product_description");
        addRequest.setProductPrice(BigDecimal.valueOf(2000));
        addRequest.setCategory(ProductCategory.CLOTHING);

       Product product = productService.findProductBy(sellerService.addProductToStore(addRequest).getProductId());
        searchRequest.setProductName("productName");
        assertEquals(product.getId(),customerService.searchForProductByName(searchRequest).getProductId());
   }
   @Test
    public void testThatCustomerCanFIndProductByCategory() {
        customerService.registerCustomer(request);
        Customer customer = customerService.findCustomer(loginRequest.getEmail());
        SearchForProductByCategoryRequest searchRequest = new SearchForProductByCategoryRequest();

        AddProductRequest addRequest = new AddProductRequest();
        addRequest.setProductName("productName");
        addRequest.setProductDescription("Product_description");
        addRequest.setProductPrice(BigDecimal.valueOf(2000));
        addRequest.setCategory(ProductCategory.CLOTHING);

        Product product = productService.findProductBy(sellerService.addProductToStore(addRequest).getProductId());
        searchRequest.setProductCategory(ProductCategory.CLOTHING);
        assertEquals(1,customerService.searchForProductByCategory(searchRequest).size());
   }

   @Test
    public void testThatCustomerCanAddItemToCart(){
        customerService.registerCustomer(request);
        Customer customer = customerService.findCustomer(loginRequest.getEmail());
        AddProductRequest addRequest = new AddProductRequest();
        addRequest.setProductName("productName");
        addRequest.setProductDescription("Product_description");
        addRequest.setProductPrice(BigDecimal.valueOf(2000));
        addRequest.setSellerId(sellerId);
        addRequest.setCategory(ProductCategory.CLOTHING);

       SearchForProductByNameRequest searchRequest = new SearchForProductByNameRequest();
        searchRequest.setProductName(addRequest.getProductName());

       Product product = productService.findProductBy(sellerService.addProductToStore(addRequest).getProductId());
       AddItemToCartRequest addItemToCartRequest = new AddItemToCartRequest();
       addItemToCartRequest.setProductId(product.getId());
       addItemToCartRequest.setCustomerId(customer.getId());
       addItemToCartRequest.setQuantity(2);

       cartServices.addItemToCart(addItemToCartRequest);
       assertEquals(1, cartServices.countCustomerItem(customer.getId()));
   }
   @Test
    public void testThatCustomerCanAddMoreItemToCart(){
        customerService.registerCustomer(request);
       Customer customer = customerService.findCustomer(loginRequest.getEmail());


       AddProductRequest addRequest = new AddProductRequest();
       addRequest.setProductName("productName");
       addRequest.setProductDescription("Product_description");
       addRequest.setProductPrice(BigDecimal.valueOf(2000));
       addRequest.setSellerId(sellerId);
       addRequest.setCategory(ProductCategory.CLOTHING);

       AddProductRequest addRequest1 = new AddProductRequest();
       addRequest1.setProductName("productName");
       addRequest1.setProductDescription("Product_description");
       addRequest1.setProductPrice(BigDecimal.valueOf(2000));
       addRequest1.setSellerId(sellerId);
       addRequest1.setCategory(ProductCategory.CLOTHING);

       SearchForProductByNameRequest searchRequest = new SearchForProductByNameRequest();
       searchRequest.setProductName(addRequest.getProductName());

       Product product = productService.findProductBy(sellerService.addProductToStore(addRequest).getProductId());
       Product product1 = productService.findProductBy(sellerService.addProductToStore(addRequest1).getProductId());

       AddItemToCartRequest addItemToCartRequest = new AddItemToCartRequest();
       addItemToCartRequest.setProductId(product.getId());
       addItemToCartRequest.setCustomerId(customer.getId());
       addItemToCartRequest.setQuantity(2);

       AddItemToCartRequest addItemToCartRequest1 = new AddItemToCartRequest();
       addItemToCartRequest1.setProductId(product1.getId());
       addItemToCartRequest1.setCustomerId(customer.getId());
       addItemToCartRequest1.setQuantity(3);

       cartServices.addItemToCart(addItemToCartRequest);
       cartServices.addItemToCart(addItemToCartRequest1);

       assertEquals(2,cartServices.countCustomerItem(customer.getId()));
   }
   @Test public void testThatCustomerCanRemoveFromCart() {
       customerService.registerCustomer(request);
       Customer customer = customerService.findCustomer(loginRequest.getEmail());


       AddProductRequest addRequest = new AddProductRequest();
       addRequest.setProductName("productName");
       addRequest.setProductDescription("Product_description");
       addRequest.setProductPrice(BigDecimal.valueOf(2000));
       addRequest.setSellerId(sellerId);
       addRequest.setCategory(ProductCategory.CLOTHING);

       AddProductRequest addRequest1 = new AddProductRequest();
       addRequest1.setProductName("productName");
       addRequest1.setProductDescription("Product_description");
       addRequest1.setProductPrice(BigDecimal.valueOf(2000));
       addRequest1.setSellerId(sellerId);
       addRequest1.setCategory(ProductCategory.CLOTHING);

       SearchForProductByNameRequest searchRequest = new SearchForProductByNameRequest();
       searchRequest.setProductName(addRequest.getProductName());

       Product product = productService.findProductBy(sellerService.addProductToStore(addRequest).getProductId());
       Product product1 = productService.findProductBy(sellerService.addProductToStore(addRequest1).getProductId());

       AddItemToCartRequest addItemToCartRequest = new AddItemToCartRequest();
       addItemToCartRequest.setProductId(product.getId());
       addItemToCartRequest.setCustomerId(customer.getId());
       addItemToCartRequest.setQuantity(2);

       AddItemToCartRequest addItemToCartRequest1 = new AddItemToCartRequest();
       addItemToCartRequest1.setProductId(product1.getId());
       addItemToCartRequest1.setCustomerId(customer.getId());
       addItemToCartRequest1.setQuantity(3);

       AddItemResponse addItemResponse =cartServices.addItemToCart(addItemToCartRequest);
       cartServices.addItemToCart(addItemToCartRequest1);

       RemoveItemFromCartRequest removeItemFromCartRequest = new RemoveItemFromCartRequest();
       removeItemFromCartRequest.setItemId(addItemResponse.getItemId());
       cartServices.removeItemFromCart(removeItemFromCartRequest);

       assertEquals(1,cartServices.countCustomerItem(customer.getId()));
   }
   @Test
    public void testCustomerCanViewCart(){
       customerService.registerCustomer(request);
       Customer customer = customerService.findCustomer(loginRequest.getEmail());


       AddProductRequest addRequest = new AddProductRequest();
       addRequest.setProductName("productName");
       addRequest.setProductDescription("Product_description");
       addRequest.setProductPrice(BigDecimal.valueOf(2000));
       addRequest.setSellerId(sellerId);
       addRequest.setCategory(ProductCategory.CLOTHING);

       AddProductRequest addRequest1 = new AddProductRequest();
       addRequest1.setProductName("productName");
       addRequest1.setProductDescription("Product_description");
       addRequest1.setProductPrice(BigDecimal.valueOf(2000));
       addRequest1.setSellerId(sellerId);
       addRequest1.setCategory(ProductCategory.CLOTHING);

       SearchForProductByNameRequest searchRequest = new SearchForProductByNameRequest();
       searchRequest.setProductName(addRequest.getProductName());

       Product product = productService.findProductBy(sellerService.addProductToStore(addRequest).getProductId());
       Product product1 = productService.findProductBy(sellerService.addProductToStore(addRequest1).getProductId());

       AddItemToCartRequest addItemToCartRequest = new AddItemToCartRequest();
       addItemToCartRequest.setProductId(product.getId());
       addItemToCartRequest.setCustomerId(customer.getId());
       addItemToCartRequest.setQuantity(2);

       AddItemToCartRequest addItemToCartRequest1 = new AddItemToCartRequest();
       addItemToCartRequest1.setProductId(product1.getId());
       addItemToCartRequest1.setCustomerId(customer.getId());
       addItemToCartRequest1.setQuantity(3);

       cartServices.addItemToCart(addItemToCartRequest);
       cartServices.addItemToCart(addItemToCartRequest1);
       assertEquals(2,cartServices.viewCart(customer.getId()).size());
   }

   @Test
    public void testThatCustomerCanPlaceOrder(){

   }
}