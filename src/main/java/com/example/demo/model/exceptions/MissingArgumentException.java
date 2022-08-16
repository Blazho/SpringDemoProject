package com.example.demo.model.exceptions;

public class MissingArgumentException extends RuntimeException{
    public MissingArgumentException(){
        super("Missing arguments exception");
    }
}
