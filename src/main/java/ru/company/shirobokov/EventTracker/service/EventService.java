package ru.company.shirobokov.EventTracker.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.company.shirobokov.EventTracker.model.Event;
import ru.company.shirobokov.EventTracker.repository.EventRepository;
import ru.company.shirobokov.EventTracker.repository.PhotoRepository;

@Service
public class EventService{
	
	private final EventRepository eventRepository;
	
	private final PhotoRepository photoRepository;
	
	@Autowired
	public EventService(EventRepository eventRepository, PhotoRepository photoRepository) {
		this.eventRepository = eventRepository;
		this.photoRepository = photoRepository;
	}
	
	public void saveEvent(Event event) {
		eventRepository.save(event);
	}
	
	public List<Event> findUnmoderatedEvents(){
		return eventRepository.findNotModerated();
	}
	
	public List<Event> findNowEvents(){
		return eventRepository.findNowModerated();
	}
	
	public List<Event> findPastEvents(){
		return eventRepository.findPastModerated();
	}
	
	public Event getByEventId(int eventId) {
		return eventRepository.findById(eventId).orElse(null);
	}
	
	public void deleteEventByEventId(int eventId) {
		Event event = eventRepository.findById(eventId).get();
		try {
			Files.delete(Paths.get(event.getPhotos().get(0).getPath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		eventRepository.delete(getByEventId(eventId));
	}
	
	public List<Event> searchEvent(String search){
		return eventRepository.findByNameStartingWith(search);
	}
	
}
