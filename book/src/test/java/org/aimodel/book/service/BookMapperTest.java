package org.aimodel.book.service;

import org.aimodel.book.controller.dto.BookDto;
import org.aimodel.book.repository.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookMapperTest {

    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        bookMapper = new BookMapper();
    }

    @Test
    void toDtoListShouldMapListOfBooksToListOfBookDtos() {
        // Given
        List<Book> books = Arrays.asList(
                new Book(1, "テスト駆動開発", "Kent Beck", "オーム社", 3080),
                new Book(2, "アジャイルサムライ", "Jonathan Rasmusson", "オーム社", 2860)
        );

        // When
        List<BookDto> bookDtos = bookMapper.toDtoList(books);

        // Then
        assertThat(bookDtos).hasSize(2);
        assertThat(bookDtos.get(0))
                .extracting(BookDto::getId, BookDto::getTitle, BookDto::getAuthor, BookDto::getPublisher, BookDto::getPrice)
                .containsExactly(1, "テスト駆動開発", "Kent Beck", "オーム社", 3080);
        assertThat(bookDtos.get(1))
                .extracting(BookDto::getId, BookDto::getTitle, BookDto::getAuthor, BookDto::getPublisher, BookDto::getPrice)
                .containsExactly(2, "アジャイルサムライ", "Jonathan Rasmusson", "オーム社", 2860);
    }

    @Test
    void toDtoListShouldReturnEmptyListWhenGivenEmptyList() {
        // Given
        List<Book> emptyList = List.of();

        // When
        List<BookDto> result = bookMapper.toDtoList(emptyList);

        // Then
        assertThat(result).isEmpty();
    }
}