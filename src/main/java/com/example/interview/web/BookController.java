package com.example.interview.web;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.interview.domain.Book;
import com.example.interview.dto.CreateBookRequest;
import com.example.interview.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public List<Book> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Book findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @GetMapping("/search")
    public List<Book> searchByTitle(@RequestParam String title) {
        return service.searchByTitle(title);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@Valid @RequestBody CreateBookRequest request) {
        return service.createBook(request);
    }

    @PatchMapping("/{id}/checkout")
    public Book checkout(@PathVariable UUID id) {
        return service.checkout(id);
    }

    @PatchMapping("/{id}/return")
    public Book returnBook(@PathVariable UUID id) {
        return service.returnBook(id);
    }
}
