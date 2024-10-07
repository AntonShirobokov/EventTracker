package ru.company.shirobokov.EventTracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.company.shirobokov.EventTracker.model.Event;
import ru.company.shirobokov.EventTracker.service.EventService;

@Controller
public class AdminPageController {
	
	private final EventService eventService;
	
	@Autowired
	public AdminPageController(EventService eventService) {
		this.eventService = eventService;
	}
	
	@GetMapping("/admin")
	public String adminPage(Model model) {
		List<Event> events = eventService.findUnmoderatedEvents();
		model.addAttribute("events", events);
		return "adminpage";
	}
	
	@PostMapping("/admin/accept")
	public String acceptEvent(@RequestParam("eventId") int eventId) {
		Event event = eventService.getByEventId(eventId);
		event.setIsModerated(true);
		eventService.saveEvent(event);
		return "redirect:/admin";
	}
	
	@PostMapping("/admin/reject")
	public String rejectEvent(@RequestParam("eventId") int eventId) {
		eventService.deleteEventByEventId(eventId);
		return "redirect:/admin";
	}
	
}
