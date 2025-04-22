package com.esa.exception;

public class DuplicateSeatAssignmentException extends RuntimeException {
    public DuplicateSeatAssignmentException(String message) {
        super(message);
    }
}
