package com.bookstore.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bookstore.domain.Book;
import com.bookstore.exceptions.BookExistException;

public interface BookService {

	List<Book> getBooks();

	Page<Book> getBooks(Pageable pageable);

	void save(Book book) throws BookExistException;

	Book getBook(Long id);

	void delete(Book book);

	Page<Book> searchBook(String searchName, Pageable pageable);

	boolean bookExist(String author, String title);
}
