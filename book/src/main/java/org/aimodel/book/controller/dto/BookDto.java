package org.aimodel.book.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {
    private Integer id;

    @NotBlank(message = "title must not be blank.")
    private String title;

    @NotBlank(message = "author must not be blank.")
    private String author;

    @NotBlank(message = "publisher must not be blank.")
    private String publisher;

    @NotNull(message = "price must not be null.")
    @Positive(message = "price must be positive.")
    private Integer price;
}