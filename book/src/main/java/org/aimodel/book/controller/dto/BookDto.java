package org.aimodel.book.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {
  private Integer id;
  private String title;
  private String author;
  private String publisher;
  private Integer price;
}
