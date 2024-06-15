package com.semicolon.africa.Estore.services;

import com.semicolon.africa.Estore.data.models.Item;
import com.semicolon.africa.Estore.data.models.Product;
import com.semicolon.africa.Estore.data.repositories.Items;
import com.semicolon.africa.Estore.dtos.request.AddItemToCartRequest;
import com.semicolon.africa.Estore.dtos.request.RemoveItemFromCartRequest;
import com.semicolon.africa.Estore.exceptions.ItemNotFoundException;
import com.semicolon.africa.Estore.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.semicolon.africa.Estore.utils.Mapper.map;

@Service
public class ItemServiceImpl implements ItemService{
    @Autowired
    private Items items;
    @Autowired
    ProductService productService;


    @Override
    public Item addItem(AddItemToCartRequest addItemToCartRequest) {
        Product product = productService.findProductBy(addItemToCartRequest.getProductId());
        if(product == null) throw new ProductNotFoundException("Product not found");
        Item item = map(addItemToCartRequest);
        items.save(item);
        return item;
    }


    @Override
    public long count() {
        return items.count();
    }

    @Override
    public Item findItem(long itemId) {
        return items.findById(itemId).orElseThrow(()->new ItemNotFoundException("No Item Found"));
    }

    @Override
    public long countCustomerItem(long customerId) {
        return getItems(customerId).size();
    }
    @Override
    public List<Item> getItems(long customerId) {
        List<Item> customerItems = items.findItemByCustomerId(customerId);
        if(customerItems.isEmpty()) throw new ItemNotFoundException("No items In The Cart");
        return customerItems;
    }

    @Override
    public void deleteItem(RemoveItemFromCartRequest removeItemFromCartRequest) {
        items.deleteById(removeItemFromCartRequest.getItemId());
    }
}
