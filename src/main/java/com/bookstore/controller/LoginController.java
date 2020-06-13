package com.bookstore.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {

	@GetMapping("/login")
	public ModelAndView loginPage(@RequestParam(value = "error",required = false) String error,
								  @RequestParam(value = "logout",	required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null)
			model.addObject("error", "Invalid Credentials provided.");
		if (logout != null)
			model.addObject("message", "Logged out from Bookstore successfully.");

		model.setViewName("login");
		return model;
	}

	@GetMapping("/profile")
	public String defaultAfterLogin(HttpServletRequest request) {
		if (request.isUserInRole("ADMIN")) {
			return "redirect:/admin/";
		}
		return "redirect:/user/";
	}

	@GetMapping("/user")
	public String showUserForm(Model theModel) {
		theModel.addAttribute("name", "Simple User");
		return "user-form";
	}
}
