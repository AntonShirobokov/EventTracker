package ru.company.shirobokov.EventTracker.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.company.shirobokov.EventTracker.model.Person;
import ru.company.shirobokov.EventTracker.repository.PeopleRepository;
import ru.company.shirobokov.EventTracker.security.PersonDetails;

@Service
public class PersonDetailsService implements UserDetailsService{
	
	@Autowired
	private PeopleRepository peopleRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Person> person = peopleRepository.findByUsername(username);
		if (person.isPresent()){
			return new PersonDetails(person.get());
			
		} else {
			throw new UsernameNotFoundException("Такого пользователя не существует");
		}
	}
	
}
