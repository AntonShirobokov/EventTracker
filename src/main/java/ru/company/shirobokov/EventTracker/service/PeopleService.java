package ru.company.shirobokov.EventTracker.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.company.shirobokov.EventTracker.model.Person;
import ru.company.shirobokov.EventTracker.repository.PeopleRepository;

@Service
public class PeopleService {
	private final PeopleRepository peopleRepository;
	
	@Autowired
	public PeopleService(PeopleRepository peopleRepository) {
		this.peopleRepository = peopleRepository;
	}
	
	public void savePerson(Person person) {
		peopleRepository.save(person);
	}
	
	public Person getPersonByPersonId(int id) {
		return peopleRepository.findById(id).orElse(null);
	}
	
	public void deleteUserByPersonId(int personId) {
		Person person = getPersonByPersonId(personId);
		String path = "src/main/resources/static/photosEvents/" + person.getUsername();
		try {
	        if(Files.exists(Paths.get(path))) {
	        	// Удаление всех файлов и поддиректорий внутри указанной директории
		        Files.walk(Paths.get(path))
		             .sorted(Comparator.reverseOrder())
		             .map(Path::toFile)
		             .forEach(File::delete);
		        // Удаление самой директории
		        Files.delete(Paths.get(path));
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		peopleRepository.delete(person);
	}
}
