package com.example.interview.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.interview.domain.Book;
import com.example.interview.dto.CreateBookRequest;
import com.example.interview.error.BookNotFoundException;
import com.example.interview.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> findAll() {
        return repository.findAll();
    }

    public Book findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book createBook(CreateBookRequest request) {
        // TODO: Reject duplicate ISBN values with DuplicateIsbnException.
        Book book = new Book(
                UUID.randomUUID(),
                request.title(),
                request.author(),
                request.isbn(),
                request.availableCopies()
        );

        return repository.save(book);
    }

    public List<Book> searchByTitle(String title) {
        // TODO: Return only books whose title contains the search text, case-insensitively.
        return repository.findAll();
    }

    public Book checkout(UUID id) {
        Book book = findById(id);
        // TODO: Throw OutOfStockException when no copies are available.
        // TODO: Decrease availableCopies by one and save the book.
        return book;
    }

    public Book returnBook(UUID id) {
        Book book = findById(id);
        // TODO: Increase availableCopies by one and save the book.
        return book;
    }
}
