package com.example.exceptions;

//not exists by API call to Product service
public class ProductNotExistsException extends RuntimeException {
    public ProductNotExistsException(String message) {
        super(message);
    }
}
