package com.bookstore.api;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.domain.Book;
import com.bookstore.service.BookService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Validated
@Slf4j
public class BookApiController {

	private static final int OVERNIGHT_HOURS = 25;
	private static final Book MOCK_BOOK = new Book(1L, "Alice in the wonderland", "author", "some", 3, BigDecimal.valueOf(9.99), ZonedDateTime.now(), ZonedDateTime.now());
	private BookService service;

	@Autowired
	public BookApiController(@Qualifier("bookServiceSimple") BookService service) {
		this.service = service;
	}

	@PostMapping("/books")
	@ApiOperation(value = "Retrieve list of books /api/books?date=YYYY-MM-DDThh:mm:ss.mmmZ", produces = APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Returns list with books which are created after specified date", response = Book.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad request or validation error", response = ApiError.class)
	})
	ResponseEntity<BookAnswer> fetchBooks(@RequestParam("date")
										  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime dateCreatedFrom) {
		log.info("Received book retrieving request.");
		List<Book> books = service.getBooksByFromDate(dateCreatedFrom);
		BookAnswer answer = new BookAnswer(books);
		return ResponseEntity.status(HttpStatus.OK).body(answer);
	}

	@GetMapping("/books")
	@ApiOperation(value = "Retrieve list of books /api/books", produces = APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Returns list with books which are created after specified date", response = Book.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad request or validation error", response = ApiError.class)
	})
	ResponseEntity<BookAnswer> fetchBooksOvernight() {
		log.info("Received book retrieving request.");
		ZonedDateTime time = ZonedDateTime.now().minusHours(OVERNIGHT_HOURS);
		List<Book> books = service.getBooksByFromDate(time);
		BookAnswer answer = new BookAnswer(books);
		return ResponseEntity.status(HttpStatus.OK).body(answer);
	}

	@GetMapping("/mock")
	@ApiOperation(value = "Retrieve list of books /api/books", produces = APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Returns mocked book ", response = Book.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad request or validation error", response = ApiError.class)
	})
	ResponseEntity<BookAnswer> mock() {
		log.info("Received book retrieving request.");
		List<Book> books = Collections.singletonList(MOCK_BOOK);
		BookAnswer answer = new BookAnswer(books);
		return ResponseEntity.status(HttpStatus.OK).body(answer);
	}



}
