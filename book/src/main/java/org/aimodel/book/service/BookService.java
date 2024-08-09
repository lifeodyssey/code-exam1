package org.aimodel.book.service;

import org.aimodel.book.repository.entity.Book;
import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();
}