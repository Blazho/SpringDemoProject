package com.example.demo.model.exceptions;

public class MissingProductInformationException extends RuntimeException{
    public MissingProductInformationException(){
        super("Missing product information exception");
    }
}
