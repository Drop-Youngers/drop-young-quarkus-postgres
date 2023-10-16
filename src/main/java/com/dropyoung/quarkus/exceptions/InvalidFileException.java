package com.dropyoung.quarkus.exceptions;

public class InvalidFileException extends Exception{
    public InvalidFileException(String message) {
        super(message);
    }
}
