package com.esa.exception;

/**
 * Exception thrown when attempting to create a resource that already exists.
 */
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
