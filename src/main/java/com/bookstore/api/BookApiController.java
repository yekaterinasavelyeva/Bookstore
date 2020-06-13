package com.bookstore.api;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.time.ZonedDateTime;
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
	ResponseEntity<List<Book>> fetchBooks(@RequestParam("date")
										  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime dateCreatedFrom) {
		log.info("Received book retrieving request.");
		List<Book> answer = service.getBooksByFromDate(dateCreatedFrom);
		return ResponseEntity.status(HttpStatus.OK).body(answer);
	}

	@GetMapping("/books")
	@ApiOperation(value = "Retrieve list of books /api/books", produces = APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Returns list with books which are created after specified date", response = Book.class, responseContainer = "List"),
			@ApiResponse(code = 400, message = "Bad request or validation error", response = ApiError.class)
	})
	ResponseEntity<List<Book>> fetchBooksOvernight() {
		log.info("Received book retrieving request.");
		ZonedDateTime time = ZonedDateTime.now().minusHours(OVERNIGHT_HOURS);
		List<Book> answer = service.getBooksByFromDate(time);
		return ResponseEntity.status(HttpStatus.OK).body(answer);
	}



}
