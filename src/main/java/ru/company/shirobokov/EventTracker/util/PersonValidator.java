package ru.company.shirobokov.EventTracker.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.company.shirobokov.EventTracker.model.Person;
import ru.company.shirobokov.EventTracker.service.RegistrationService;

@Component
public class PersonValidator implements Validator {
	
	private final RegistrationService registrationService;
	
	@Autowired
	public PersonValidator(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Person person = (Person) target;
		if (registrationService.checkUsernameOnUniqueness(person.getUsername())) {
			errors.rejectValue("username", "", "Пользователь с таким username уже существует");
		}
	}
}
