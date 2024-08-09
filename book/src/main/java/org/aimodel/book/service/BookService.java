package org.aimodel.book.service;

import java.util.List;
import org.aimodel.book.controller.dto.BookDto;

public interface BookService {
  List<BookDto> getAllBooks();
}
