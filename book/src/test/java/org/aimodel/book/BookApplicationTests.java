package org.aimodel.book;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.aimodel.book.controller.dto.BookDto;
import org.aimodel.book.controller.dto.GetAllBooksResponse;
import org.aimodel.book.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookApplicationTests extends ApplicationTestBase {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @AfterEach
    void tearDown() {
        bookRepository.deleteById(5);
    }

    @SneakyThrows
    @Test
    void getAllBooksShouldReturnAllBooksFromDatabase() {
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

    @SneakyThrows
    @Test
    void createBookShouldReturnCreatedStatusAndLocation() {
        // Given
        BookDto newBook = new BookDto(null, "Clean Code: A Handbook of Agile Software Craftsmanship",
                "Robert C. Martin", "Pearson", 6430);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BookDto> request = new HttpEntity<>(newBook, headers);

        // When
        ResponseEntity<Void> response = restTemplate.postForEntity("/books", request, Void.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation()).isNotNull();
        assertThat(response.getHeaders().getLocation().toString()).contains("/books/");
    }

    @SneakyThrows
    @Test
    void createBookWithBlankTitleShouldReturnBadRequest() {
        // Given
        BookDto invalidBook = new BookDto(null, "", "Robert C. Martin", "ドワンゴ", 2640);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BookDto> request = new HttpEntity<>(invalidBook, headers);

        // When
        ResponseEntity<String> response = restTemplate.postForEntity("/books", request, String.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();

        JsonNode errorResponse = objectMapper.readTree(response.getBody());
        assertThat(errorResponse.get("type").asText()).isEqualTo("about:blank");
        assertThat(errorResponse.get("title").asText()).isEqualTo("request validation error is occurred.");
        assertThat(errorResponse.get("detail").asText()).isEqualTo("title must not be blank.");
        assertThat(errorResponse.get("instance").asText()).isEqualTo("/books");
    }
}
