package com.example.bankcards.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleResourceNotFoundException(ResourceNotFoundException error) {
        return new ExceptionBody(error.getMessage());
    }
    
    @ExceptionHandler(ResourceMappingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handleResourceMappingException(ResourceMappingException error) {
        return new ExceptionBody(error.getMessage());
    }

    @ExceptionHandler(OperationDeniedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleOperaionDeniedException(ResourceMappingException error) {
        return new ExceptionBody(error.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleIllegalStateException(IllegalStateException error) {
        return new ExceptionBody(error.getMessage());
    }

    @ExceptionHandler({AccessDeniedException.class, org.springframework.security.access.AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleAccessDeniedException() {
        return new ExceptionBody("Access denied.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleMethodArgumentNotValidException(MethodArgumentNotValidException error) {
        List<FieldError> fieldErrors = error.getBindingResult().getFieldErrors();
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed.");
        exceptionBody.setError(fieldErrors.stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
        return exceptionBody;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleConstraintViolationException(ConstraintViolationException error) {
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed.");
        exceptionBody.setError(
            error.getConstraintViolations().stream().collect(Collectors.toMap(
                violation -> violation.getPropertyPath().toString(),
                ConstraintViolation::getMessage
            ))
        );
        return exceptionBody;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handleException(Exception error) {
        return new ExceptionBody("Internal error.");
    }
}
