package com.example.demo.model.exceptions;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long id){
        super(String.format("Product with id:%d not found exception",id));
    }
}
