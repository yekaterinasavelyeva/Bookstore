package com.bookstore.api;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bookstore.domain.Book;
import com.bookstore.exceptions.NotFoundException;
import com.bookstore.service.BookServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(BookApiController.class)
class BookApiControllerTest {

	private static final ZoneId ZONE_FI = ZoneId.of("Europe/Helsinki");
	private static final ZonedDateTime TIME = ZonedDateTime.of(2020, 6, 13, 11,0,0,0, ZONE_FI);


	@Autowired
	private MockMvc mockMvc;

	@MockBean
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
					   .price(BigDecimal.valueOf(1000,2))
					   .createdTime(TIME.plusHours(5))
					   .updatedTime(TIME.plusHours(3))
					   .build();
	}

	@Test
	public void getMock_ReturnsHttpStatusOk() throws Exception {

		mockMvc.perform(
				get("/api/mock")
						.contentType(MediaType.APPLICATION_JSON)
						.content(""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title", is("Alice in the wonderland")))
				.andDo(print());
	}

	@Test
	public void getBookOvernight_ReturnsHttpStatusOk() throws Exception {
		given(service.getBooksByFromDate(ArgumentMatchers.argThat( a -> a.isBefore(ZonedDateTime.now())))).willReturn(Collections.singletonList(book));

		mockMvc.perform(
				get("/api/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content(""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title", is("Test Book")))
				.andExpect(jsonPath("$[0].price", is("10.00")))
				.andDo(print());
	}

	@Test
	public void getBookByDate_ReturnsHttpStatusOk() throws Exception {
		given(service.getBooksByFromDate(ArgumentMatchers.argThat( a -> a.isBefore(ZonedDateTime.now())))).willReturn(Collections.singletonList(book));

		mockMvc.perform(
				post("/api/books")
						.param("date", "2020-06-10T00:00:00.000Z")
						.contentType(MediaType.APPLICATION_JSON)
						.content(""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title", is("Test Book")))
				.andExpect(jsonPath("$[0].price", is("10.00")))
				.andDo(print());
	}

	@Test
	public void getBookByDateParamUrl_ReturnsHttpStatusOk() throws Exception {
		given(service.getBooksByFromDate(ArgumentMatchers.argThat( a -> a.isBefore(ZonedDateTime.now())))).willReturn(Collections.singletonList(book));

		mockMvc.perform(
				post("/api/books?date=2020-06-10T00:00:00.000Z")
						.contentType(MediaType.APPLICATION_JSON)
						.content(""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title", is("Test Book")))
				.andExpect(jsonPath("$[0].price", is("10.00")))
				.andDo(print());
	}

	@Test
	public void getBookByDate_ReturnsHttpStatusNotFound() throws Exception {
		when(service.getBooksByFromDate(ArgumentMatchers.any())).thenThrow(new NotFoundException("Book not found by date"));

		mockMvc.perform(
				get("/api/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content(""))
				.andExpect(status().isNotFound())
				.andExpect(content().json("{\"status\":\"NOT_FOUND\",\"message\":\"Book not found by date\",\"errors\":[\"Information not found\"]}"))
				.andExpect(jsonPath("$.message", is("Book not found by date")))
				.andExpect(jsonPath("$.status", is("NOT_FOUND")))
				.andExpect(jsonPath("$.errors", hasItems("Information not found")))
				.andDo(print());
	}

	@Test
	public void getBookByDate_ReturnsHttpStatusInternalError() throws Exception {
		when(service.getBooksByFromDate(ArgumentMatchers.any())).thenThrow(new RuntimeException("Something went wrong"));

		mockMvc.perform(
				post("/api/books?date=2020-06-10T00:00:00.000Z")
						.contentType(MediaType.APPLICATION_JSON)
						.content(""))
				.andExpect(status().isInternalServerError())
				.andExpect(content().json("{\"status\":\"INTERNAL_SERVER_ERROR\",\"message\":\"Something went wrong\",\"errors\":[\"error occurred\"]}"))
				.andExpect(jsonPath("$.message", is("Something went wrong")))
				.andExpect(jsonPath("$.status", is("INTERNAL_SERVER_ERROR")))
				.andExpect(jsonPath("$.errors", hasItems("error occurred")))
				.andDo(print());
	}

	@Test
	public void getBookByDateMissingParameter_ReturnsHttpStatusBadRequest() throws Exception {

		mockMvc.perform(
				post("/api/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content(""))
				.andExpect(status().isBadRequest())
				.andExpect(content().json("{\"status\":\"BAD_REQUEST\",\"message\":\"Required ZonedDateTime parameter 'date' is not present\",\"errors\":[\"Validation errors occurred\"]}"))
				.andExpect(jsonPath("$.message", is("Required ZonedDateTime parameter 'date' is not present")))
				.andExpect(jsonPath("$.status", is("BAD_REQUEST")))
				.andExpect(jsonPath("$.errors", hasItems("Validation errors occurred")))
				.andDo(print());
	}


}