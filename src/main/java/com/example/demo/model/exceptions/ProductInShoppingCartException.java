package com.example.demo.model.exceptions;

public class ProductInShoppingCartException extends RuntimeException{
    public ProductInShoppingCartException(Long id){
        super(String.format("Product with id:%d is in shopping cart", id));

    }
}
