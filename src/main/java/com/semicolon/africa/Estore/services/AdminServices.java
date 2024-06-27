package com.semicolon.africa.Estore.services;

import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.semicolon.africa.Estore.dtos.request.*;
import com.semicolon.africa.Estore.dtos.response.*;

import java.io.IOException;
import java.util.List;

public interface AdminServices {
    RegisterAdminResponse register(RegisterAdminRequest request);
    LoginResponse login(LoginRequest request);
    ApproveOrderResponse approveOrder(ApproveOrderRequest request);
    AddProductResponse addProductToStore(AddProductRequest addProduct) throws IOException;

    String deleteProduct(DeleteProductRequest deleteProductRequest);

    EditProductResponse editProduct(Long id, JsonPatch patch) throws JsonPatchException;

    long count();

    String deactivateAccount(Long id);

    void deleteAll();

    FIndAdminResponse findAdmin(String email);

    List<AddProductResponse> findAllProductAdded();

}
