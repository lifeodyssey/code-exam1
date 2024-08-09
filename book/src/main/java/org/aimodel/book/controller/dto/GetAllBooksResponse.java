package org.aimodel.book.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetAllBooksResponse {
    private List<BookDto> books;
}
