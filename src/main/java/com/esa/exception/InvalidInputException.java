package com.esa.exception;

/**
 * Exception thrown when the provided input is invalid.
 */
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}
