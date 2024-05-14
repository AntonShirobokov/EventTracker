package ru.company.shirobokov.EventTracker.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.company.shirobokov.EventTracker.model.Person;
import ru.company.shirobokov.EventTracker.repository.PeopleRepository;


@Service
public class RegistrationService {
	
	private PeopleRepository peopleRepository;
	
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
		this.peopleRepository = peopleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional
	public void register(Person person) {
		person.setPassword(passwordEncoder.encode(person.getPassword()));
		person.setRole("ROLE_USER");
		peopleRepository.save(person);
	}
	
	@Transactional
	public boolean checkUsernameOnUniqueness(String username) {
		Optional<Person> person = peopleRepository.findByUsername(username);
		return person.isPresent();
	}
}
