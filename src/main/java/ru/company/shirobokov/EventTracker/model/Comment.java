package ru.company.shirobokov.EventTracker.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="comment")
public class Comment {

	@Id
	@Column(name="comment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int commentId;
	
	@Column(name="message")
	private String message;
	
	@Column(name="date_of_sending")
	private LocalDate dateOfSending;
	
	@Column(name="time_of_sending")
	private LocalTime timeOfSending;
	
	@ManyToOne
	@JoinColumn(name="event_id", referencedColumnName = "event_id")
	private Event event;
	
	@ManyToOne
	@JoinColumn(name="person_id", referencedColumnName = "person_id")
	private Person person;
	
	public Comment() {
	}

	public Comment(String message, LocalDate dateOfSending, LocalTime timeOfSending, Event event, Person person) {
		super();
		this.message = message;
		this.dateOfSending = dateOfSending;
		this.timeOfSending = timeOfSending;
		this.event = event;
		this.person = person;
	}



	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDate getDateOfSending() {
		return dateOfSending;
	}

	public void setDateOfSending(LocalDate dateOfSending) {
		this.dateOfSending = dateOfSending;
	}

	public LocalTime getTimeOfSending() {
		return timeOfSending;
	}

	public void setTimeOfSending(LocalTime timeOfSending) {
		this.timeOfSending = timeOfSending;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", text=" + message + ", dateOfSending=" + dateOfSending
				+ ", timeOfSending=" + timeOfSending + ", event=" + event + ", person=" + person + "]";
	}
	
	public String getFormatedLocalTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		String formattedTime = timeOfSending.format(formatter);
		return formattedTime;
	}
}
