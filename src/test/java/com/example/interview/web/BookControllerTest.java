package com.example.interview.web;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.interview.dto.CreateBookRequest;
import com.example.interview.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void createBookValidatesRequestBody() throws Exception {
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "",
                                  "author": "Joshua Bloch",
                                  "isbn": "9780134685991",
                                  "availableCopies": 1
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("title")));
    }

    @Test
    void duplicateIsbnReturnsConflict() throws Exception {
        String requestBody = objectMapper.writeValueAsString(
                new CreateBookRequest("Effective Java", "Joshua Bloch", "9780134685991", 1)
        );

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", containsString("9780134685991")));
    }

    @Test
    void searchReturnsMatchingBooksOnly() throws Exception {
        createBook("Clean Code", "Robert C. Martin", "9780132350884", 2);
        createBook("Effective Java", "Joshua Bloch", "9780134685991", 1);

        mockMvc.perform(get("/api/books/search").param("title", "clean"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title").value("Clean Code"));
    }

    @Test
    void checkoutReturnsUpdatedBook() throws Exception {
        String response = createBook("Effective Java", "Joshua Bloch", "9780134685991", 2);
        String id = objectMapper.readTree(response).get("id").asText();

        mockMvc.perform(patch("/api/books/{id}/checkout", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableCopies").value(1));
    }

    private String createBook(String title, String author, String isbn, int availableCopies) throws Exception {
        CreateBookRequest request = new CreateBookRequest(title, author, isbn, availableCopies);

        return mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
