package org.aimodel.book;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.aimodel.book.controller.dto.BookDto;
import org.aimodel.book.controller.dto.GetAllBooksResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookApplicationTests extends ApplicationTestBase {

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  @SneakyThrows
  @Test
  void getAllBooksShouldReturnAllBooksFromDatabase()  {
    // Given
    List<BookDto> expectedBooks =
        List.of(
            new BookDto(1, "テスト駆動開発", "Kent Beck", "オーム社", 3080),
            new BookDto(2, "アジャイルサムライ", "Jonathan Rasmusson", "オーム社", 2860),
            new BookDto(3, "エクストリームプログラミング", "Kent Beck", "オーム社", 2420),
            new BookDto(4, "Clean Agile", "Robert C. Martin", "ドワンゴ", 2640));
    GetAllBooksResponse expectedResponse = new GetAllBooksResponse(expectedBooks);
    String expectedJson = objectMapper.writeValueAsString(expectedResponse);

    // When
    ResponseEntity<String> response = restTemplate.getForEntity("/books", String.class);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();

    // Compare JSON trees
    JsonNode actualJsonNode = objectMapper.readTree(response.getBody());
    JsonNode expectedJsonNode = objectMapper.readTree(expectedJson);
    assertThat(actualJsonNode).isEqualTo(expectedJsonNode);
  }
}
