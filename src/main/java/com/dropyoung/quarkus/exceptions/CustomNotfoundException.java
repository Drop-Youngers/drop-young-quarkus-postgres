package com.dropyoung.quarkus.exceptions;

public class CustomNotfoundException extends RuntimeException {
    public CustomNotfoundException(String message) {
        super(message);
    }
}