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
    void toDtoShouldMapBookToBookDto() {
        // Given
        Book book = new Book(1, "テスト駆動開発", "Kent Beck", "オーム社", 3080);

        // When
        BookDto bookDto = bookMapper.toDto(book);

        // Then
        assertThat(bookDto)
                .extracting(BookDto::getId, BookDto::getTitle, BookDto::getAuthor, BookDto::getPublisher, BookDto::getPrice)
                .containsExactly(1, "テスト駆動開発", "Kent Beck", "オーム社", 3080);
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

    @Test
    void toEntityShouldMapBookDtoToBook() {
        // Given
        BookDto bookDto = new BookDto(null, "新しい本", "新しい著者", "新しい出版社", 1000);

        // When
        Book book = bookMapper.toEntity(bookDto);

        // Then
        assertThat(book)
                .extracting(Book::getId, Book::getTitle, Book::getAuthor, Book::getPublisher, Book::getPrice)
                .containsExactly(null, "新しい本", "新しい著者", "新しい出版社", 1000);
    }

    @Test
    void toEntityShouldNotSetIdWhenMappingFromBookDto() {
        // Given
        BookDto bookDto = new BookDto(1, "本", "著者", "出版社", 1000);

        // When
        Book book = bookMapper.toEntity(bookDto);

        // Then
        assertThat(book.getId()).isNull();
        assertThat(book)
                .extracting(Book::getTitle, Book::getAuthor, Book::getPublisher, Book::getPrice)
                .containsExactly("本", "著者", "出版社", 1000);
    }
}