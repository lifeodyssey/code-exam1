package org.aimodel.book.repository;

import org.aimodel.book.repository.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {}
