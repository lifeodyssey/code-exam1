package org.aimodel.book.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.aimodel.book.controller.dto.BookDto;
import org.aimodel.book.repository.BookRepository;
import org.aimodel.book.repository.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BookServiceImplTest {
  @Mock
  private BookRepository bookRepository;
  @Mock
  private BookMapper bookMapper;

  @InjectMocks
  private BookServiceImpl bookService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }
  @Test
  void getAllBooksShouldReturnAllBooksAsDto() {
    // Given
    List<Book> books =
        List.of(
            new Book(1, "テスト駆動開発", "Kent Beck", "オーム社", 3080),
            new Book(2, "アジャイルサムライ", "Jonathan Rasmusson", "オーム社", 2860));
    List<BookDto> expectedBookDtos =
        List.of(
            new BookDto(1, "テスト駆動開発", "Kent Beck", "オーム社", 3080),
            new BookDto(2, "アジャイルサムライ", "Jonathan Rasmusson", "オーム社", 2860));

    when(bookRepository.findAll()).thenReturn(books);
    when(bookMapper.toDtoList(books)).thenReturn(expectedBookDtos);

    // When
    List<BookDto> actualBookDtos = bookService.getAllBooks();

    // Then
    assertThat(actualBookDtos).isEqualTo(expectedBookDtos);
    verify(bookRepository, times(1)).findAll();
    verify(bookMapper, times(1)).toDtoList(books);
  }

  @Test
  void getAllBooksShouldReturnEmptyListWhenNoBooks() {
    // Given
    when(bookRepository.findAll()).thenReturn(List.of());
    when(bookMapper.toDtoList(List.of())).thenReturn(List.of());

    // When
    List<BookDto> actualBookDtos = bookService.getAllBooks();

    // Then
    assertThat(actualBookDtos).isEmpty();
    verify(bookRepository, times(1)).findAll();
    verify(bookMapper, times(1)).toDtoList(List.of());
  }

  @Test
  void createBookShouldSaveAndReturnBookAsDto() {
    // Given
    BookDto inputBookDto = new BookDto(null, "新しい本", "新しい著者", "新しい出版社", 1000);
    Book inputBook = new Book(null, "新しい本", "新しい著者", "新しい出版社", 1000);
    Book savedBook = new Book(1, "新しい本", "新しい著者", "新しい出版社", 1000);
    BookDto expectedBookDto = new BookDto(1, "新しい本", "新しい著者", "新しい出版社", 1000);

    when(bookMapper.toEntity(inputBookDto)).thenReturn(inputBook);
    when(bookRepository.save(inputBook)).thenReturn(savedBook);
    when(bookMapper.toDto(savedBook)).thenReturn(expectedBookDto);

    // When
    BookDto actualBookDto = bookService.createBook(inputBookDto);

    // Then
    assertThat(actualBookDto).isEqualTo(expectedBookDto);
    verify(bookMapper, times(1)).toEntity(inputBookDto);
    verify(bookRepository, times(1)).save(inputBook);
    verify(bookMapper, times(1)).toDto(savedBook);
  }
}
