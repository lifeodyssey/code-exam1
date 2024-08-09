package org.aimodel.book.repository;

import jakarta.transaction.Transactional;
import org.aimodel.book.ApplicationTestBase;
import org.aimodel.book.repository.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BookRepositoryTest extends ApplicationTestBase {

    @Autowired
    private BookRepository bookRepository;


    @Test
    void shouldFindAllBooks() {
        List<Book> expectedBooks =
                List.of(
                        new Book(1, "テスト駆動開発", "Kent Beck", "オーム社", 3080),
                        new Book(2, "アジャイルサムライ", "Jonathan Rasmusson", "オーム社", 2860),
                        new Book(3, "エクストリームプログラミング", "Kent Beck", "オーム社", 2420),
                        new Book(4, "Clean Agile", "Robert C. Martin", "ドワンゴ", 2640));
        List<Book> books = bookRepository.findAll();
        assertThat(books).isEqualTo(expectedBooks);
    }

    @Test
    void shouldSaveNewBook() {
        // Given
        Book newBook = Book.builder()
                .title("新しい本")
                .author("新しい著者")
                .publisher("新しい出版社")
                .price(1000)
                .build();
        // When
        Book savedBook = bookRepository.save(newBook);

        // Then
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getId()).isEqualTo(5);
        assertThat(savedBook.getTitle()).isEqualTo("新しい本");
        assertThat(savedBook.getAuthor()).isEqualTo("新しい著者");
        assertThat(savedBook.getPublisher()).isEqualTo("新しい出版社");
        assertThat(savedBook.getPrice()).isEqualTo(1000);

        Book foundBook = bookRepository.findById(savedBook.getId()).orElse(null);
        assertThat(foundBook).usingRecursiveComparison().isEqualTo(savedBook);
    }
}
