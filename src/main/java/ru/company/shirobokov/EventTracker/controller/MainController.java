package ru.company.shirobokov.EventTracker.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ru.company.shirobokov.EventTracker.model.Person;
import ru.company.shirobokov.EventTracker.security.PersonDetails;

@Controller
public class MainController {

	@GetMapping
	public String homePage() {
		return "home";
	}

	@GetMapping("/profile")
	public String profilePage(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
		Person person = personDetails.getPerson();
		model.addAttribute("person", person);
		return "profile";
	}

	@GetMapping("/admin")
	public String adminPage() {
		return "adminpage";
	}

}
