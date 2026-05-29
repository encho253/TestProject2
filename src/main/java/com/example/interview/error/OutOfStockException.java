package com.example.interview.error;

import java.util.UUID;

public class OutOfStockException extends RuntimeException {

    public OutOfStockException(UUID id) {
        super("Book with id " + id + " is out of stock");
    }
}
