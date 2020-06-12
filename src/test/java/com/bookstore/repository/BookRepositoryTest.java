package com.bookstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.bookstore.domain.Book;

@DataJpaTest
class BookRepositoryTest {

	@Autowired
	private BookRepository repository;

	@Test
	void getByIdTest() {
		Optional<Book> book  = repository.findById(1L);
		assertTrue(book.isPresent());
		assertEquals("Alice in Wonderland", book.get().getTitle());
		assertEquals("Lewis Caroll", book.get().getAuthor());
		assertEquals(BigDecimal.valueOf(3500, 2), book.get().getPrice());
	}

	@Test
	void testGetBooksByPageable() {
		Pageable pageable = PageRequest.of(0, 2, Sort.by("createdTime"));
		Page<Book> page =  repository.findAll(pageable);
		Book book = page.getContent().iterator().next();
		assertEquals("Alice in Wonderland", book.getTitle());
		assertEquals("Lewis Caroll", book.getAuthor());
		assertEquals(BigDecimal.valueOf(3500, 2), book.getPrice());
	}

	@Test
	void testBookSearch() {
		Pageable pageable = PageRequest.of(0, 2, Sort.by("createdTime"));
		Page<Book> page = repository.searchBook("%alice%", pageable);
		assertEquals("Alice in Wonderland", page.getContent().iterator().next().getTitle());
	}

}