package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.model.ShoppingCart;

public interface ShoppingCartService {

    public ShoppingCart findUsersCart(String username);
    public Product addProduct(Long productId, String username);
    public void deleteProduct(Long productId, String username);
}
