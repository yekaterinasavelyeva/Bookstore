package com.bookstore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookstore.domain.Book;
import com.bookstore.exceptions.BookExistException;
import com.bookstore.service.BookService;

@Controller
public class AdminController {

	private BookService service;

	@Autowired
	public AdminController(@Qualifier("bookServiceSimple") BookService service) {
		this.service = service;
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/admin")
	public String showFromForAdd(Model theModel) {
		Book book = new Book();
		theModel.addAttribute("book", book);
		return "book-form";
	}

	@RequestMapping("/admin/saveBook")
	public String saveBook(@Valid @ModelAttribute("book") Book book, BindingResult result, Model theModel) {
		if (result.hasErrors())
			return "book-form";
		try {
			service.save(book);
			theModel.addAttribute("message", "Book added successfully.");
		} catch (BookExistException e) {
			theModel.addAttribute("exception", e.getMessage());
		}
		return "book-form";
	}
}
