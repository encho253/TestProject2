package com.example.interview.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.interview.domain.Book;

public interface BookRepository {

    List<Book> findAll();

    Optional<Book> findById(UUID id);

    Optional<Book> findByIsbn(String isbn);

    Book save(Book book);

    void deleteAll();
}
