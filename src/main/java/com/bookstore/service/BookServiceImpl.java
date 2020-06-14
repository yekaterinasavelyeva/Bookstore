package com.bookstore.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.domain.Book;
import com.bookstore.exceptions.BookExistException;
import com.bookstore.exceptions.InvalidPropertyException;
import com.bookstore.exceptions.NotFoundException;
import com.bookstore.repository.BookRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(value = "bookServiceSimple")
public class BookServiceImpl implements BookService{

	private BookRepository repository;

	@Autowired
	public BookServiceImpl(BookRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Book> getBooks() {
		return repository.getBooks();
	}

	@Override
	public List<Book> getBooksByFromDate(ZonedDateTime dateFrom) {
		requireNonNull(dateFrom, "Failed to retrieve books. Date property is null");
		List<Book> books =  repository.getBooksByFromDate(dateFrom);
		if(books.isEmpty()){
			log.error("Failed to fetch books items from DB by from date : {}" , dateFrom);
			throw new NotFoundException(String.format("Book items not found by from date : %s" , dateFrom));
		}
		return books;
	}

	@Override
	public Page<Book> getBooks(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Book book) throws BookExistException {
		if(!bookExist(book.getTitle()))
			repository.save(book);
		else throw new BookExistException("This book already exist!");
	}

	@Override
	public Book getBook(Long id) {
		return repository.getOne(id);
	}

	@Override
	@Transactional
	public void delete(Book book) {
		repository.delete(book);
	}

	@Override
	@Transactional
	public Page<Book> searchBook(String searchName, Pageable pageable) {
		searchName = "%" + searchName.trim().toLowerCase() + "%";
		return repository.searchBook(searchName, pageable);
	}

	@Override
	@Transactional
	public boolean bookExist(String title) {
		return !repository.getBookByTitle(title).isEmpty();
	}

	private static void requireNonNull(Object obj, String message) {
		if (obj == null) {
			log.error("Property required : {}", message);
			throw new InvalidPropertyException(message);
		}
	}


}
