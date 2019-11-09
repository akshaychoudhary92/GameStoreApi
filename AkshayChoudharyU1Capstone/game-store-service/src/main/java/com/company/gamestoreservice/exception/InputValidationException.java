package com.company.gamestoreservice.exception;

public class InputValidationException extends RuntimeException{
    public InputValidationException() {
    }
    public InputValidationException(String message) {
        super(message);
    }
}
