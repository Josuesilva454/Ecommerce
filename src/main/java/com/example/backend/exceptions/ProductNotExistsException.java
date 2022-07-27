package com.example.backend.exceptions;

public class ProductNotExistsException extends  IllegalArgumentException {
    public ProductNotExistsException(String msg){
        super(msg);
    }
}
