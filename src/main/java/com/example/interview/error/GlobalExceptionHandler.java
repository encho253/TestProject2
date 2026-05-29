package com.example.interview.error;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(BookNotFoundException exception) {
        return error(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(DuplicateIsbnException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDuplicateIsbn(DuplicateIsbnException exception) {
        return error(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(OutOfStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleOutOfStock(OutOfStockException exception) {
        return error(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidation(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .orElse("Request validation failed");

        return error(HttpStatus.BAD_REQUEST, message);
    }

    private ApiError error(HttpStatus status, String message) {
        return new ApiError(Instant.now(), status.value(), status.getReasonPhrase(), message);
    }
}
