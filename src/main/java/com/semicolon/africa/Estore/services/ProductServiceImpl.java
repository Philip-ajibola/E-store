package com.semicolon.africa.Estore.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.semicolon.africa.Estore.data.models.Product;
import com.semicolon.africa.Estore.data.models.ProductCategory;
import com.semicolon.africa.Estore.data.repositories.Products;
import com.semicolon.africa.Estore.dtos.request.*;
import com.semicolon.africa.Estore.dtos.response.AddProductResponse;
import com.semicolon.africa.Estore.dtos.response.EditProductResponse;
import com.semicolon.africa.Estore.exceptions.InvalidProductNameException;
import com.semicolon.africa.Estore.exceptions.ProductNotFoundException;
import com.semicolon.africa.Estore.utils.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.semicolon.africa.Estore.utils.Mapper.map;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    Products products;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public long count() {
        return products.count();
    }

    @Override
    public AddProductResponse addProduct(AddProductRequest addProduct) throws IOException {
        validateProductName(addProduct.getProductName());
        Map map = ObjectUtils.asMap("resource_type","image");
        Map<?,?> response = cloudinary.uploader().upload(addProduct.getFile().getBytes(), map);
        String url = response.get("url").toString();
       Product product = mapper.map(addProduct,Product.class);
       product.setUrl(url);
       product = products.save(product);
       return mapper.map(product,AddProductResponse.class);
    }

    @Override
    public String deleteProduct(DeleteProductRequest deleteProductRequest) {
        Product product = findProductById(deleteProductRequest.getProductId());
        products.delete(product);
        return "Product deleted";
    }

    @Override
    public Product findProductBy(Long productId) {
        return products.findById(productId).orElseThrow(()-> new ProductNotFoundException("Product Not Found"));
    }

    @Override
    public EditProductResponse editProduct(Long id, JsonPatch patch) throws JsonPatchException {
        Product product = findProductById(id);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.convertValue(product, JsonNode.class);
        node = patch.apply(node);
        product = mapper.convertValue(node, Product.class);
        product = products.save(product);
        return this.mapper.map(product,EditProductResponse.class);
    }

    @Override
    public void deleteAll() {
        products.deleteAll();
    }

    @Override
    public List<AddProductResponse> findProductByName(SearchForProductByNameRequest searchRequest) {
        List<Product> productList = products.findProductByProductName(searchRequest.getProductName());
        if(productList.isEmpty()) throw new ProductNotFoundException("Product Not Found");
        return productList.stream().map(product -> mapper.map(product,AddProductResponse.class)).toList();
    }

    @Override
    public List<AddProductResponse> findProductByCategory(ProductCategory productCategory) {
        List<Product> productList = products.findProductByCategory(productCategory);
        return productList.stream().map(Mapper::map).collect(Collectors.toList());
    }

    @Override
    public List<AddProductResponse> findAll() {
        List<Product> productList = products.findAll();
        List<AddProductResponse> productResponses = productList.stream().map((product) -> mapper.map(product,AddProductResponse.class)).toList();
        if(productList.isEmpty())throw new ProductNotFoundException("No Product Found");
        return productResponses;
    }


    public Product findProductById(long productId) {
        return products.findById(productId).orElseThrow(()-> new ProductNotFoundException("Product not Found"));
    }

    private void validateProductName(String productName) {
        if(productName == null || productName.isBlank()) throw new InvalidProductNameException("Invalid Product. Product Name must not be empty or null");
    }


}
