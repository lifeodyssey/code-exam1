package org.aimodel.book.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

import java.util.List;
import org.aimodel.book.repository.BookRepository;
import org.aimodel.book.repository.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BookServiceImplTest {
  @Mock private BookRepository bookRepository;

  @InjectMocks private BookServiceImpl bookService;

  @Test
  void getAllBooks_shouldReturnAllBooks() {
    // Given
    List<Book> expectedBooks =
        List.of(
            new Book(1, "テスト駆動開発", "Kent Beck", "オーム社", 3080),
            new Book(2, "アジャイルサムライ", "Jonathan Rasmusson", "オーム社", 2860));
    when(bookRepository.findAll()).thenReturn(expectedBooks);

    // When
    List<Book> actualBooks = bookService.getAllBooks();

    // Then
    assertThat(actualBooks).isEqualTo(expectedBooks);
    verify(bookRepository, times(1)).findAll();
  }
}
