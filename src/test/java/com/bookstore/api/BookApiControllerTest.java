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

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookServiceImpl service;

	@Test
	public void getMock_ReturnsHttpStatusOk() throws Exception {

		mockMvc.perform(
				get("/api/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content(""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title", is("Alice in the wonderland")))
				.andDo(print());
	}

}