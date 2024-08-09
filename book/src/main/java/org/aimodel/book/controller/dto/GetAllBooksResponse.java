package org.aimodel.book.controller.dto;

import java.util.List;

public record GetAllBooksResponse(List<BookDto> books) {
    public GetAllBooksResponse {
        books = List.copyOf(books);
    }
}