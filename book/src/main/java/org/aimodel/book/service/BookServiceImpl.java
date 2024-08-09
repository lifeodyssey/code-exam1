package org.aimodel.book.service;

import java.util.List;
import org.aimodel.book.repository.BookRepository;
import org.aimodel.book.repository.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
  private final BookRepository bookRepository;

  @Autowired
  public BookServiceImpl(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }
}
