package ru.company.shirobokov.EventTracker.controller;

import java.nio.file.Path;
import java.util.Collections;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ru.company.shirobokov.EventTracker.model.Event;
import ru.company.shirobokov.EventTracker.model.Person;
import ru.company.shirobokov.EventTracker.model.Photo;
import ru.company.shirobokov.EventTracker.repository.EventRepository;
import ru.company.shirobokov.EventTracker.repository.PhotoRepository;
import ru.company.shirobokov.EventTracker.security.PersonDetails;
import ru.company.shirobokov.EventTracker.service.EventService;
import ru.company.shirobokov.EventTracker.service.PhotoService;

@Controller
public class CreateEventController {
	
	private final EventService eventService;
	
	private final PhotoService photoService;
	
	public CreateEventController(EventService eventService, PhotoService photoService) {
		this.eventService = eventService;
		this.photoService = photoService;
	}
	
	
	
	@GetMapping("/createevent")
	public String createEventPage(Model model) { //страница создания события
		model.addAttribute("event", new Event());
		return "createevent";
	}
	
	@PostMapping("/createevent") //добавление события в базу данных
	public String createEvent(@ModelAttribute("event") Event event, @RequestParam("file") MultipartFile file) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
		Person person = personDetails.getPerson();
		
		event.setCreator(person);
		event.setIsModerated(false);
		
		Photo photo = new Photo();
		photo.setEvent(event);
		
		String uniqueFileName = UUID.randomUUID().toString() + ".jpeg";
		
		String uploadPath = "src/main/resources/static/photosEvents/" + person.getUsername();
		String path = photoService.savePhoto(uniqueFileName, uploadPath, file);
		
		photo.setFileName(uniqueFileName);
		photo.setPath(path);
		event.setPhotos(Collections.singletonList(photo));
		
		eventService.saveEvent(event);
		
		return "redirect:/";
	}
	
}
