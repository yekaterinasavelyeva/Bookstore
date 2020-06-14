package com.bookstore.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ValidationTest {

	private static Validator validator;

	@BeforeAll
	static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@ParameterizedTest
	@MethodSource("provideInvalidBooks")
	void testValidation(Book book) {
		Set<ConstraintViolation<Book>> violations = validator.validate(book);
		System.out.println("-----> " + violations);
		assertFalse(violations.isEmpty());
	}

	@Test
	void testValidationWhenNoFieldsThenThrowException() {
		Book book = new Book(1L,"", "", null, null, BigDecimal.valueOf(3535353.355), ZonedDateTime.now(), ZonedDateTime.now());
		List<String> messages = Arrays.asList("numeric value out of bounds (<6 digits>.<2 digits> expected)","is required","size must be between 2 and 30", "is required", "is required", "is required", "size must be between 2 and 50");
		Set<ConstraintViolation<Book>> violations = validator.validate(book);
		violations.forEach(v -> assertTrue(messages.contains(v.getMessage())));
		assertEquals(messages.size(), violations.size());
	}

	private static Stream<Arguments> provideInvalidBooks() {
		return Stream.of(
				Arguments.of(new Book()),
				Arguments.of(new Book(1L,"L", "A", "", 1, BigDecimal.valueOf(35), ZonedDateTime.now(), ZonedDateTime.now())),
				Arguments.of(new Book(1L,"", "", null, null, BigDecimal.valueOf(3535353.355), ZonedDateTime.now(), ZonedDateTime.now())),
				Arguments.of(new Book(1L,null, null, "", 0, null , ZonedDateTime.now(), ZonedDateTime.now()))
		);
	}
}
