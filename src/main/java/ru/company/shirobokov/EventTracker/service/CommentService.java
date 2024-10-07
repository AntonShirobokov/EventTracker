package ru.company.shirobokov.EventTracker.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.company.shirobokov.EventTracker.model.Comment;
import ru.company.shirobokov.EventTracker.model.Event;
import ru.company.shirobokov.EventTracker.repository.CommentRepository;

@Service
public class CommentService {
	private final CommentRepository commentRepository;
	
	@Autowired
	public CommentService(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}
	
	public void saveComment(Comment comment) {
		comment.setDateOfSending(LocalDate.now());
		comment.setTimeOfSending(LocalTime.now());
		commentRepository.save(comment);
	}
	
	public List<Comment> getAllOrderCommentsForEvent(Event event) {
		return commentRepository.getOrderComments(event.getEventId());
	}
}
