package com.example.demo.model.exceptions;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(){
        super("User with same username already exists exception");
    }
}
