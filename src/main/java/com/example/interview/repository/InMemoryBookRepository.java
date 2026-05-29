package com.example.interview.repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import com.example.interview.domain.Book;

@Repository
public class InMemoryBookRepository implements BookRepository {

    private final ConcurrentMap<UUID, Book> books = new ConcurrentHashMap<>();

    @Override
    public List<Book> findAll() {
        return books.values().stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .toList();
    }

    @Override
    public Optional<Book> findById(UUID id) {
        return Optional.ofNullable(books.get(id));
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return books.values().stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(UUID.randomUUID());
        }

        books.put(book.getId(), book);
        return book;
    }

    @Override
    public void deleteAll() {
        books.clear();
    }
}
