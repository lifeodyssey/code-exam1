package org.aimodel.book.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aimodel.book.controller.dto.BookDto;
import org.aimodel.book.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void getAllBooksShouldReturnListOfBooks() throws Exception {
        // Given
        List<BookDto> books = List.of(
                new BookDto(1, "テスト駆動開発", "Kent Beck", "オーム社", 3080),
                new BookDto(2, "アジャイルサムライ", "Jonathan Rasmusson", "オーム社", 2860)
        );
        when(bookService.getAllBooks()).thenReturn(books);

        // When & Then
        mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.books").isArray())
                .andExpect(jsonPath("$.books.length()").value(2))
                .andExpect(jsonPath("$.books[0].id").value(1))
                .andExpect(jsonPath("$.books[0].title").value("テスト駆動開発"))
                .andExpect(jsonPath("$.books[0].author").value("Kent Beck"))
                .andExpect(jsonPath("$.books[0].publisher").value("オーム社"))
                .andExpect(jsonPath("$.books[0].price").value(3080))
                .andExpect(jsonPath("$.books[1].id").value(2))
                .andExpect(jsonPath("$.books[1].title").value("アジャイルサムライ"))
                .andExpect(jsonPath("$.books[1].author").value("Jonathan Rasmusson"))
                .andExpect(jsonPath("$.books[1].publisher").value("オーム社"))
                .andExpect(jsonPath("$.books[1].price").value(2860));
    }

    @Test
    void getAllBooksShouldReturnEmptyListWhenNoBooks() throws Exception {
        // Given
        when(bookService.getAllBooks()).thenReturn(List.of());

        // When & Then
        mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.books").isArray())
                .andExpect(jsonPath("$.books.length()").value(0));
    }

    @Test
    void createBookShouldReturnCreatedStatus() throws Exception {
        // Given
        BookDto inputBook = new BookDto(null, "Clean Agile", "Robert C. Martin", "ドワンゴ", 2640);
        BookDto createdBook = new BookDto(3, "Clean Agile", "Robert C. Martin", "ドワンゴ", 2640);
        when(bookService.createBook(any(BookDto.class))).thenReturn(createdBook);

        // When & Then
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputBook)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/books/3"));
    }

    @ParameterizedTest
    @CsvSource({
            "title,'',title must not be blank.",
            "author,'',author must not be blank.",
            "publisher,'',publisher must not be blank.",
            "price,0,price must be positive."
    })
    void createBookShouldReturnBadRequestWhenFieldsAreInvalid(String field, String value, String expectedError) throws Exception {
        // Given
        BookDto inputBook = new BookDto(null, "Clean Agile", "Robert C. Martin", "ドワンゴ", 2640);

        // Set the invalid field
        switch (field) {
            case "title" -> inputBook.setTitle(value);
            case "author" -> inputBook.setAuthor(value);
            case "publisher" -> inputBook.setPublisher(value);
            case "price" -> inputBook.setPrice(Integer.parseInt(value));
        }

        // When & Then
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputBook)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("request validation error is occurred."))
                .andExpect(jsonPath("$.detail").value(expectedError))
                .andExpect(jsonPath("$.instance").value("/books"));
    }

    @Test
    void createBookShouldReturnBadRequestWhenPriceIsNull() throws Exception {
        // Given
        BookDto inputBook = new BookDto(null, "Clean Agile", "Robert C. Martin", "ドワンゴ", null);

        // When & Then
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputBook)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("request validation error is occurred."))
                .andExpect(jsonPath("$.detail").value("price must not be null."))
                .andExpect(jsonPath("$.instance").value("/books"));
    }
}