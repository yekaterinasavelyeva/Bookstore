package com.bookstore.api;

import com.bookstore.domain.Book;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Value;

@Data
@Value
@ApiModel
public class BookAnswer {

	private final Book book;
}
