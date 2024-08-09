package org.aimodel.book.service;

import java.util.List;

import org.aimodel.book.controller.dto.BookDto;
import org.aimodel.book.repository.BookRepository;
import org.aimodel.book.repository.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
  private final BookRepository bookRepository;
  private final BookMapper bookMapper;


  @Autowired
  public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
    this.bookRepository = bookRepository;
    this.bookMapper = bookMapper;
  }

  @Override
  public List<BookDto> getAllBooks() {
    List<Book> books = bookRepository.findAll();
    return bookMapper.toDtoList(books);
  }
}
