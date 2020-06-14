package com.bookstore.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bookstore.api.BookApiController;
import com.bookstore.domain.Book;
import com.bookstore.service.BookServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(BookApiController.class)
class HomeControllerTest {

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
	public void getHomePage_ReturnsHttpStatusOk() throws Exception {
		PageImpl<Book> page = new PageImpl(Collections.singletonList(book));

		given(service.getBooks(ArgumentMatchers.any())).willReturn(page);
		mockMvc.perform(
				get("/list")
						.contentType(MediaType.ALL_VALUE)
						.content(""))
				.andExpect(status().isOk())
				.andExpect(view().name("list-books"))
				.andExpect(model().attribute("data", page.getContent()))
				.andDo(print());
	}

	@Test
	public void getHomeSearch_ReturnsHttpStatusOk() throws Exception {
		PageImpl<Book> page = new PageImpl(Collections.singletonList(book));

		given(service.searchBook(anyString(), argThat(a -> a instanceof Pageable))).willReturn(page);
		mockMvc.perform(
				get("/search")
						.param("theSearchName", "test")
						.contentType(MediaType.ALL_VALUE)
						.content(""))
				.andExpect(status().isOk())
				.andExpect(view().name("list-books"))
				.andExpect(model().attribute("data", page.getContent()))
				.andDo(print());
	}

	@Test
	public void testHomePage() throws Exception {
		this.mockMvc.perform(get("/index.html"))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/list"))
				.andDo(print())
				.andReturn();
	}

}