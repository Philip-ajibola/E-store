package com.semicolon.africa.Estore.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.semicolon.africa.Estore.dtos.request.*;
import com.semicolon.africa.Estore.dtos.response.*;
import com.semicolon.africa.Estore.services.AdminServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@Validated
@RequestMapping("/api/v1")
public class AdminController {
    @Autowired
    private AdminServices adminServices;

    @PostMapping("/sign-up")
    public ResponseEntity<RegisterAdminResponse> signUp(@RequestBody @Valid RegisterAdminRequest request){
        var result = adminServices.register(request);
        return ResponseEntity.status(CREATED).body(result);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request)  {
        var result = adminServices.login(request);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/approve_order")
    public ResponseEntity<ApproveOrderResponse> approveOrder(ApproveOrderRequest request){
        var result = adminServices.approveOrder(request);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/add_product")
    public ResponseEntity<AddProductResponse> addProductToStore(@RequestBody AddProductRequest addProduct) throws IOException {
        var result = adminServices.addProductToStore(addProduct);
        return ResponseEntity.status(CREATED).body(result);
    }
    @PatchMapping("/edit_product")
    public ResponseEntity<EditProductResponse> editProduct(@RequestBody Long id, @RequestBody JsonPatch patch) throws JsonPatchException {
        var result = adminServices.editProduct(id, patch);
        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/delete_product")
    public ResponseEntity<?> deleteProduct(@RequestBody DeleteProductRequest deleteProductRequest){
        var result = adminServices.deleteProduct(deleteProductRequest);
        return ResponseEntity.ok(result);
    }
   @DeleteMapping("/de_activate_acc/{id}")
    public ResponseEntity<?> deActivateAcc(@PathVariable("id") long id){
        var result = adminServices.deactivateAccount(id);
        return ResponseEntity.ok(result);

   }
   @GetMapping("/find_admin/")
    public ResponseEntity<FIndAdminResponse> findAdmin(@Param("email") String email){
        var result = adminServices.findAdmin(email);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/find_all_product_added")
    public ResponseEntity<List<AddProductResponse>> findAllProductAdded(){
        var result = adminServices.findAllProductAdded();
        return ResponseEntity.ok(result);
    }
}
