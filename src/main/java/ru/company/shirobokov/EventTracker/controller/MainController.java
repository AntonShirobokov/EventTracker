package ru.company.shirobokov.EventTracker.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.company.shirobokov.EventTracker.model.Comment;
import ru.company.shirobokov.EventTracker.model.Event;
import ru.company.shirobokov.EventTracker.model.Person;
import ru.company.shirobokov.EventTracker.security.PersonDetails;
import ru.company.shirobokov.EventTracker.service.CommentService;
import ru.company.shirobokov.EventTracker.service.EventService;
import ru.company.shirobokov.EventTracker.service.PeopleService;

@Controller
public class MainController {
	
	private final PeopleService peopleService;
	
	private final EventService eventService;
	
	private final CommentService commentService;
	
	
	public MainController(PeopleService peopleService, EventService eventService, CommentService commentService) {
		this.peopleService = peopleService;
		this.eventService = eventService;
		this.commentService = commentService;
	}
	

	@GetMapping
	public String homePage(Model model) {
		model.addAttribute("events", eventService.findNowEvents());
		return "home";
	}
	
	@GetMapping("/pastevents")
	public String pastevents(Model model) {
		model.addAttribute("events", eventService.findPastEvents());
		return "pastevents";
	}
	

	@GetMapping("/profile")
	public String profilePage(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
		Person person = personDetails.getPerson();
		
		model.addAttribute("person", peopleService.getPersonByPersonId(person.getPersonId()));
		return "profile";
	}

	@GetMapping("/event/{eventId}")
	public String showEventInfo(@PathVariable("eventId") int eventId , Model model) {
		Event event = eventService.getByEventId(eventId);
		List<Comment> comments = commentService.getAllOrderCommentsForEvent(event);
		model.addAttribute("event", event);
		model.addAttribute("comments", comments);
		return "showevent";
	}
	
	@PostMapping("/event/{eventId}/addcomment")
	public String addComment(@PathVariable("eventId") int eventId, @RequestParam("message") String message) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
		Person person = personDetails.getPerson();
		
		Person personnew = peopleService.getPersonByPersonId(person.getPersonId());
		
		Event event = eventService.getByEventId(eventId);
		
		Comment comment = new Comment();
		comment.setEvent(event);
		comment.setPerson(personnew);
		comment.setMessage(message);
		
		commentService.saveComment(comment);
		
		return "redirect:/event/" + eventId;
	}
	
	@GetMapping("/profile/edit")
	public String editProfile(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
		Person person = personDetails.getPerson();
		
		model.addAttribute("person", peopleService.getPersonByPersonId(person.getPersonId()));
		
		
		
		return "editprofile";
	}
	
	@PostMapping("/profile/edit")
	public String saveChangeProfile(@ModelAttribute("person") Person person) {
		Person personOld = peopleService.getPersonByPersonId(person.getPersonId());
		personOld.setFirstName(person.getFirstName());
		personOld.setLastName(person.getLastName());
		personOld.setFatherName(person.getFatherName());
		personOld.setPhoneNumber(person.getPhoneNumber());
		
		peopleService.savePerson(personOld);
		return "redirect:/profile";
	}
	
	
	@PostMapping("/event/{eventId}/joinevent")
	public String joinEvent(@PathVariable("eventId") int eventId) {
		System.out.println("Зашли в меетодЗашли в меетодЗашли в меетодЗашли в меетодЗашли в меетодЗашли в меетод");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
		Person person = personDetails.getPerson();
		
		Person personNew = peopleService.getPersonByPersonId(person.getPersonId());
		Event event = eventService.getByEventId(eventId);
		
		List<Person> participants = event.getParticipants();
		if (participants == null || participants.size() == 0) {
			event.setParticipants(new ArrayList<>(List.of(personNew)));
		} else {
			participants.add(personNew);
			event.setParticipants(participants);
		}
		
		List<Event> goToEvents = personNew.getGoToEvents();
		if(goToEvents == null || goToEvents.size() == 0) {
			personNew.setGoToEvents(new ArrayList<>(List.of(event)));
		} else {
			goToEvents.add(event);
			personNew.setGoToEvents(goToEvents);
		}
		
		peopleService.savePerson(personNew);
		eventService.saveEvent(event);
		
		return "redirect:/event/" + eventId;
	}
	
	@GetMapping("/event/{eventId}/viewparticipants")
	public String viewParticipants(@PathVariable("eventId") int eventId, Model model) {
		model.addAttribute("event", eventService.getByEventId(eventId));
		return "viewparticipants";
	}
	
	@GetMapping("/participant/{personId}")
	public String participantInfo(@PathVariable("personId") int personId, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
		Person person = personDetails.getPerson();
		
		if (personId == person.getPersonId()) {
			return "redirect:/profile";
		}
		else {
			model.addAttribute("person", peopleService.getPersonByPersonId(personId));
			return "participantinfo";
		}
		
	}
	
	@GetMapping("/search")
	public String resultOfSearch(@RequestParam("searchrequest") String searchrequest, Model model) {
		System.out.println(searchrequest);
		model.addAttribute("searchrequest", searchrequest);
		model.addAttribute("events", eventService.searchEvent(searchrequest));
		return "resultsearch";
	}
	
	@DeleteMapping("/event/{eventId}/delete")
	public String deleteEvent(@PathVariable("eventId") int eventId) {
		eventService.deleteEventByEventId(eventId);
		return "redirect:/";
	}
	
	@DeleteMapping("/participant/{personId}/delete")
	public String deleteUser(@PathVariable("personId") int personId) {
		peopleService.deleteUserByPersonId(personId);
		return "redirect:/";
	}
	
}
