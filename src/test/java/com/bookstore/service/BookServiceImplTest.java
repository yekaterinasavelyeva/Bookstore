package com.bookstore.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bookstore.domain.Book;
import com.bookstore.exceptions.BookExistException;
import com.bookstore.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

	private static final ZonedDateTime TIME = ZonedDateTime.of(2020, 6, 13, 11,0,0,0, ZoneId.systemDefault());

	@Mock
	BookRepository repository;

	@InjectMocks
	private BookServiceImpl service;
	private Book book;

	@BeforeEach
	void init() {
		book = Book
				.builder()
				.id(1L)
				.title("Test Book")
				.author("Test Author")
				.genre("Test")
				.pagesCount(5)
				.price(BigDecimal.TEN)
				.createdTime(ZonedDateTime.now())
				.updatedTime(ZonedDateTime.now())
				.build();
	}

	@Test
	void getBooksByFromDateTest() {
		assertNotNull(service);
		when(repository.getBooksByFromDate(TIME)).thenReturn(Collections.singletonList(book));
		List<Book> books = service.getBooksByFromDate(TIME);
		assertEquals(book, books.iterator().next());
	}

	@Test
	void saveTestSuccess() {
		when(repository.save(book)).thenReturn(book);
		when(repository.getBookByTitle(book.getTitle())).thenReturn(Collections.emptyList());
		assertDoesNotThrow(() -> service.save(book));
	}

	@Test
	void saveTestFailure() {
		when(repository.getBookByTitle(book.getTitle())).thenReturn(Collections.singletonList(book));
		assertThrows(BookExistException.class, () -> service.save(book));
	}

	@Test
	void testSearchBook() {
		when(repository.searchBook("%test%", Pageable.unpaged())).thenReturn(Page.empty());
		assertEquals(Page.empty(), service.searchBook("Test", Pageable.unpaged()));
	}

}