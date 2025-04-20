package com.example.exceptions;

public class ProductNotExistsException extends RuntimeException {
    public ProductNotExistsException(String message) {
        super(message);
    }
}
