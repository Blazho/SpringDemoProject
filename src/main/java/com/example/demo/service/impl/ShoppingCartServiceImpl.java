package com.example.demo.service.impl;

import com.example.demo.model.Product;
import com.example.demo.model.ShoppingCart;
import com.example.demo.model.User;
import com.example.demo.model.exceptions.ProductAlreadyInShoppingCart;
import com.example.demo.model.exceptions.ProductNotFoundException;
import com.example.demo.model.exceptions.UserNotFoundException;
import com.example.demo.respository.ProductRepository;
import com.example.demo.respository.ShoppingCartRepository;
import com.example.demo.respository.UserRepository;
import com.example.demo.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository,
                                   UserRepository userRepository,
                                   ProductRepository productRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ShoppingCart findUsersCart(String username) {
        User user = this.userRepository.findById(username).orElseThrow(UserNotFoundException::new);
        Optional<ShoppingCart> cart = this.shoppingCartRepository.findByUser(user);
        if (cart.isEmpty()) {
            return this.shoppingCartRepository.save(new ShoppingCart(user));
        }
        return cart.get();
    }

    @Override
    public Product addProduct(Long productId, String username) {
        ShoppingCart cart = this.findUsersCart(username);
        if (cart.getProductList().stream().anyMatch(r-> Objects.equals(r.getId(), productId))){
            throw new ProductAlreadyInShoppingCart(productId);
        }
        Product product = this.productRepository.findById(productId).orElseThrow(()-> new ProductNotFoundException(productId));
        cart.getProductList().add(product);
        this.shoppingCartRepository.save(cart);
        return product;
    }

    @Override
    public void deleteProduct(Long productId, String username) {
        ShoppingCart cart = this.findUsersCart(username);
        cart.getProductList().removeIf(r-> Objects.equals(r.getId(), productId));
        this.shoppingCartRepository.save(cart);
    }
}
