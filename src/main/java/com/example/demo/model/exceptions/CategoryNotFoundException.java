package com.example.demo.model.exceptions;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(Long id){
        super(String.format("Category with id:%d not found exception", id));
    }
}
