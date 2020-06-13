package com.bookstore.api;

import java.util.List;

import com.bookstore.domain.Book;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Value;

@Data
@Value
@ApiModel
public class BookAnswer {

	private final List<Book> bookList;

}
