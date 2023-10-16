package com.dropyoung.quarkus.exceptions;

import lombok.Getter;
import lombok.Setter;

public class CustomInternalServerException extends RuntimeException {
    public CustomInternalServerException(String message) {
        super(message);
    }
}