package ru.company.shirobokov.EventTracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import ru.company.shirobokov.EventTracker.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>{
	
    @Query(value = "SELECT * FROM event WHERE Event.is_moderated = false", nativeQuery = true)
    List<Event> findNotModerated();
    
    @Query(value = "SELECT * FROM event WHERE is_moderated = true AND (start_date + start_time) >= now()", nativeQuery = true)
    List<Event> findNowModerated();

    @Query(value = "SELECT * FROM event WHERE is_moderated = true AND (start_date + start_time) < now()", nativeQuery = true)
    List<Event> findPastModerated();
    
    List<Event> findByNameStartingWith(String name);
}
