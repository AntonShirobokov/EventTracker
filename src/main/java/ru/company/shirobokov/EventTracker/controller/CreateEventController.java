package ru.company.shirobokov.EventTracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CreateEventController {
	
	@GetMapping("/createevent")
	public String createEventPage() { //страница создания события
		return "createevent";
	}
	
}
