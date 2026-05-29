# Interviewer Guide

Use this project as a practical Spring Boot exercise for a junior Java developer.

## Suggested Flow

1. Give the candidate 5 minutes to read `README.md` and run `mvn test`.
2. Ask them to explain one failing test before they start coding.
3. Let them implement the service behavior.
4. Ask follow-up questions while they work, but avoid steering every line.
5. Reserve 10 minutes for review and discussion.

## What To Look For

- Can they read failing tests and infer expected behavior?
- Do they understand `@RestController`, request validation, and HTTP status codes?
- Do they use clear Java control flow instead of over-engineering?
- Do they write or update tests when they find an edge case?
- Do they keep exceptions meaningful and small?
- Do they notice case-insensitive search and duplicate ISBN requirements?

## Possible Follow-Up Questions

- What would change if this repository used a real database?
- Where would you put transaction boundaries?
- How would you make ISBN comparison more robust?
- Why should the service layer throw domain-specific exceptions instead of returning `null`?
- What other validation would you add?
- How would you handle concurrent checkout requests?

## Expected Implementation Notes

The simplest good solution is enough:

- `createBook` checks `repository.findByIsbn(request.isbn())` before saving.
- `searchByTitle` normalizes both strings with `toLowerCase()`.
- `checkout` throws `OutOfStockException` when `availableCopies == 0`.
- `checkout` and `returnBook` update the existing book and save it.

There is no need to add a database, Lombok, MapStruct, security, or extra architecture for this exercise.
