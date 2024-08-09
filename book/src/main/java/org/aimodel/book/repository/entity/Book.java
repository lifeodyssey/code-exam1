package org.aimodel.book.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 100, nullable = false)
  private String title;

  @Column(length = 100, nullable = false)
  private String author;

  @Column(length = 100, nullable = false)
  private String publisher;

  @Column(nullable = false)
  private Integer price;

}
