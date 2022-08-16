package com.example.demo.model.exceptions;

public class ManufacturerNotFoundException extends RuntimeException{
    public ManufacturerNotFoundException(Long id){
        super(String.format("Manufacturer with id:%d not found",id));
    }
}
