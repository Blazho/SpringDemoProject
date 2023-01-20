package com.example.demo.model.exceptions;

public class NameNullOrEmptyException extends RuntimeException{

    public NameNullOrEmptyException(){
        super("Name null or empty exception");
    }
}
