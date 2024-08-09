package org.aimodel.book.service;

import java.util.List;
import org.aimodel.book.controller.dto.BookDto;
import org.aimodel.book.repository.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDto toDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getPrice()
        );
    }

    public List<BookDto> toDtoList(List<Book> books) {
        return books.stream()
                .map(this::toDto)
                .toList();
    }

    public Book toEntity(BookDto bookDto) {
        return  Book.builder()
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .publisher(bookDto.getPublisher())
                .price(bookDto.getPrice())
                .build();
    }
}
