package org.aimodel.book.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aimodel.book.controller.dto.BookDto;
import org.aimodel.book.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;


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
    void getAllBooksShouldReturnEmptyList_whenNoBooks() throws Exception {
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
}