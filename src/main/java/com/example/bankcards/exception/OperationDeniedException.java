package com.example.bankcards.exception;

public class OperationDeniedException extends RuntimeException {
    public OperationDeniedException(String message) {
        super(message);
    }
}
