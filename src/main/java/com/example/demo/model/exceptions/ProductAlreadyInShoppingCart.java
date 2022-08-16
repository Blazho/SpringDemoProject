package com.example.demo.model.exceptions;

public class ProductAlreadyInShoppingCart extends RuntimeException{
    public ProductAlreadyInShoppingCart(Long id){
        super(String.format("Product with id:%d already in shopping cart", id));
    }
}
