package com.semicolon.africa.Estore.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.semicolon.africa.Estore.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AdminController adminController;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtService jwtService;

    @Test
    void signUp() throws Exception {
        register();
    }

    private void register() throws Exception {
        String requestBody = "{\"email\":\"ajibolaphilip10@gmail.com\",\"password\":\"password\",\"username\":\"philip\",\"phone_number\":\"09027531222\"}";
        mockMvc.perform(post("/api/v1/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isCreated()).andDo(print());
    }

    @Test
    void login() throws Exception {
        userLogin();
    }

    private String userLogin() throws Exception {
        String requestBody = "{\"email\":\"ajibolaphilip10@gmail.com\",\"password\":\"password\"}";
        register();
        String result =  mockMvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(result);
        return node.get("token").asText();
    }

    @Test
    void approveOrder() {

    }

    @Test
    @WithMockUser
    void addProductToStore() throws Exception {
        Path path = Paths.get("C:\\Users\\Dell\\Downloads\\E-store\\src\\main\\resources\\static\\thanos.jpeg");
        String token = userLogin();
        System.out.println(token);
        try(InputStream inputStream = Files.newInputStream(path)){
            MultipartFile file = new MockMultipartFile("file",inputStream);
        mockMvc.perform(multipart("/api/v1/add_product")
                .file(file.getName(),file.getBytes())
                .part(new MockPart("productName","tooth brush".getBytes()))
                .part(new MockPart("productDescription","tooth brush is use for brushing ".getBytes()))
                .part(new MockPart("productPrice","5000".getBytes()))
                .part(new MockPart("category","CLOTHING".getBytes()))
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated()).andDo(print());
        }catch (IOException e) {
            System.out.println(e);
        }
    }

    @Test
    void editProduct() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void deActivateAcc() {
    }

    @Test
    void findAdmin() {
    }

    @Test
    void findAllProductAdded() {
    }
}