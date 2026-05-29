package com.example.interview.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.interview.domain.Book;
import com.example.interview.dto.CreateBookRequest;
import com.example.interview.error.DuplicateIsbnException;
import com.example.interview.error.OutOfStockException;
import com.example.interview.repository.BookRepository;
import com.example.interview.repository.InMemoryBookRepository;

class BookServiceTest {

    private BookRepository repository;
    private BookService service;

    @BeforeEach
    void setUp() {
        repository = new InMemoryBookRepository();
        service = new BookService(repository);
    }

    @Test
    void rejectsDuplicateIsbn() {
        service.createBook(new CreateBookRequest("Clean Code", "Robert C. Martin", "9780132350884", 2));

        assertThatThrownBy(() -> service.createBook(new CreateBookRequest(
                "Clean Architecture",
                "Robert C. Martin",
                "9780132350884",
                1
        ))).isInstanceOf(DuplicateIsbnException.class);
    }

    @Test
    void searchesByTitleCaseInsensitively() {
        service.createBook(new CreateBookRequest("Clean Code", "Robert C. Martin", "9780132350884", 2));
        service.createBook(new CreateBookRequest("Effective Java", "Joshua Bloch", "9780134685991", 1));
        service.createBook(new CreateBookRequest("Java Concurrency in Practice", "Brian Goetz", "9780321349606", 1));

        assertThat(service.searchByTitle("java"))
                .extracting(Book::getTitle)
                .containsExactly("Effective Java", "Java Concurrency in Practice");
    }

    @Test
    void checkoutDecreasesAvailableCopies() {
        Book book = service.createBook(new CreateBookRequest("Effective Java", "Joshua Bloch", "9780134685991", 2));

        Book checkedOut = service.checkout(book.getId());

        assertThat(checkedOut.getAvailableCopies()).isEqualTo(1);
        assertThat(service.findById(book.getId()).getAvailableCopies()).isEqualTo(1);
    }

    @Test
    void checkoutRejectsOutOfStockBook() {
        Book book = service.createBook(new CreateBookRequest("Refactoring", "Martin Fowler", "9780134757599", 0));

        assertThatThrownBy(() -> service.checkout(book.getId()))
                .isInstanceOf(OutOfStockException.class);
    }

    @Test
    void returnBookIncreasesAvailableCopies() {
        Book book = service.createBook(new CreateBookRequest("Domain-Driven Design", "Eric Evans", "9780321125217", 1));

        Book returned = service.returnBook(book.getId());

        assertThat(returned.getAvailableCopies()).isEqualTo(2);
        assertThat(service.findById(book.getId()).getAvailableCopies()).isEqualTo(2);
    }
}
