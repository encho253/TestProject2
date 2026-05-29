# Junior Spring Boot Interview Exercise

This is a small Spring Boot kata for interviewing a junior Java developer.
The candidate should make the failing tests pass without changing the test expectations.

Requires Java 21.

## Scenario

You are building a tiny library inventory API. A book has:

- `id`
- `title`
- `author`
- `isbn`
- `availableCopies`

The app uses an in-memory repository so the candidate can focus on Java, Spring MVC, validation, exceptions, and service logic instead of database setup.

## Candidate Tasks

1. Implement the TODOs in `BookService`.
2. Reject duplicate ISBN values when creating a book.
3. Search books by title, case-insensitively.
4. Make checkout decrease `availableCopies` by one.
5. Reject checkout when the book is out of stock.
6. Make returning a book increase `availableCopies` by one.
7. Keep validation and HTTP status behavior working.

## Run

```bash
mvn test
```

The tests intentionally fail at the start. A good junior candidate should be able to read the failures, inspect the API, and implement the missing behavior in 45-75 minutes.

To run the application:

```bash
mvn spring-boot:run
```

Then try:

```bash
curl http://localhost:8080/api/books
```

Swagger UI:

- `http://localhost:8080/swagger-ui.html`
- `http://localhost:8080/v3/api-docs`

## API

- `GET /api/books`
- `GET /api/books/{id}`
- `GET /api/books/search?title=clean`
- `POST /api/books`
- `PATCH /api/books/{id}/checkout`
- `PATCH /api/books/{id}/return`

Example create request:

```json
{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "9780132350884",
  "availableCopies": 3
}
```
