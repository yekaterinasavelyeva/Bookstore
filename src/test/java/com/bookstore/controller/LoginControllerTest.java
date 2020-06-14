package com.bookstore.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import com.bookstore.api.BookApiController;
import com.bookstore.service.BookServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(BookApiController.class)
class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookServiceImpl service;

	@ParameterizedTest
	@ValueSource(strings = {"/user", "/admin", "/login", "/profile"} )
	public void testHomePageLogin(String url) throws Exception {
		mockMvc.perform(get("/user"))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("http://localhost/login"))
				.andDo(print())
				.andReturn();
	}

	@ParameterizedTest
	@ValueSource(strings = {"user", "admin"} )
	public void testHomePageUserLogin(String login) throws Exception {
		RequestBuilder requestBuilder = formLogin().user(login).password(login);
		 mockMvc.perform(requestBuilder)
				.andExpect(status().isFound())
				 .andExpect(redirectedUrl("/profile"))
				.andDo(print())
				.andReturn();
	}

	@Test
	public void testProfileLoginUnsuccessful() throws Exception {
		RequestBuilder requestBuilder = formLogin().user("some").password("user");
		mockMvc.perform(requestBuilder)
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/login?error"))
				.andDo(print())
				.andReturn();
	}

}