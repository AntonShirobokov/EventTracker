package ru.company.shirobokov.EventTracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.company.shirobokov.EventTracker.model.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
	public Optional<Person> findByUsername(String username); 
}
