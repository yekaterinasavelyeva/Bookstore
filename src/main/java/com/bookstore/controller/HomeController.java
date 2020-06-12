package com.bookstore.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookstore.domain.Book;
import com.bookstore.service.BookService;

@Controller
public class HomeController {

	private BookService service;

	@Autowired
	public HomeController(@Qualifier("bookServiceSimple") BookService service) {
		this.service = service;
	}

	@GetMapping("/index.html")
	public String showHome() {
		return "redirect:/list";
	}

	@GetMapping("/list")
	public String listCustomers(Model model, @PageableDefault(sort = {"createdTime"}, value = 50, direction = Sort.Direction.DESC)
														 Pageable pageable) {

		Page<Book> page = service.getBooks(pageable);
		model.addAttribute("number", page.getNumber());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalElements", page.getTotalElements());
		model.addAttribute("size", page.getSize());
		model.addAttribute("data",page.getContent());
		return "list-books";
	}

	@GetMapping("/search")
	public String searchBook(@RequestParam("theSearchName") String theSearchName, Model model, @PageableDefault(sort = {"createdTime"}, value = 50, direction = Sort.Direction.DESC)
																										   Pageable pageable) {
		Page<Book> page = service.searchBook(theSearchName, pageable);
		model.addAttribute("number", page.getNumber());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalElements", page.getTotalElements());
		model.addAttribute("size", page.getSize());
		model.addAttribute("data", page.getContent());
		return "list-books";
	}
}
