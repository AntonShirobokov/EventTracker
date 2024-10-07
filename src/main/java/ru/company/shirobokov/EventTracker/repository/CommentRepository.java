package ru.company.shirobokov.EventTracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.company.shirobokov.EventTracker.model.Comment;
import ru.company.shirobokov.EventTracker.model.Event;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{
	
	List<Comment> findByEvent(Event event);
	
	
	@Query(value = "SELECT * FROM comment WHERE comment.event_id = :event_id order by comment.date_of_sending, comment.time_of_sending", nativeQuery = true)
    List<Comment> getOrderComments(@Param("event_id") int eventId);
}