package org.aimodel.book.controller;

import org.aimodel.book.controller.dto.BookDto;
import org.aimodel.book.controller.dto.GetAllBooksResponse;
import org.aimodel.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping
    public ResponseEntity<GetAllBooksResponse> getAllBooks() {
        List<BookDto> bookDtos = bookService.getAllBooks();
        return ResponseEntity.ok(new GetAllBooksResponse(bookDtos));
    }

}
