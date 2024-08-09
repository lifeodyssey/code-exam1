package org.aimodel.book.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.aimodel.book.repository.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
class BookRepositoryTest {

  @Autowired private BookRepository bookRepository;

  @Container
  @ServiceConnection
  private static final PostgreSQLContainer<?> postgreSQLContainer =
          new PostgreSQLContainer<>("postgres:latest")
              .withDatabaseName("bookdb")
              .withUsername("test")
              .withPassword("test");

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    registry.add("spring.datasource.driver-class-name", postgreSQLContainer::getDriverClassName);
  }

  @Test
  void shouldFindAllBooks() {
    List<Book> expectedBooks =
        List.of(
            new Book(1,"テスト駆動開発", "Kent Beck", "オーム社", 3080),
            new Book(2,"アジャイルサムライ", "Jonathan Rasmusson", "オーム社", 2860),
            new Book(3,"エクストリームプログラミング", "Kent Beck", "オーム社", 2420),
            new Book(4,"Clean Agile", "Robert C. Martin", "ドワンゴ", 2640));
    List<Book> books = bookRepository.findAll();
    assertThat(books).isEqualTo(expectedBooks);
  }
}
