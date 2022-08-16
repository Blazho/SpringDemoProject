package com.example.demo.model.exceptions;

public class IllegalUserCredentialsException extends RuntimeException{
    public IllegalUserCredentialsException(){
        super("Invalid User credentials exception");
    }
}
