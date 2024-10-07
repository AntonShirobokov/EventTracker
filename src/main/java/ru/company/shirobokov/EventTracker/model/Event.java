package ru.company.shirobokov.EventTracker.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import ru.company.shirobokov.EventTracker.security.PersonDetails;

@Entity
@Table(name = "event")
public class Event {

	@Id
	@Column(name = "event_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int eventId;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "start_time")
	private LocalTime startTime;

	@Column(name = "address")
	private String address;

	@Column(name = "is_moderated")
	private Boolean isModerated;

	@ManyToOne
	@JoinColumn(name = "person_id", referencedColumnName = "person_id")
	private Person creator;

	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
	private List<Photo> photos;

	@OneToMany(mappedBy = "event")
	private List<Comment> comments;
	
	@ManyToMany
	@JoinTable(name="event_person",
				joinColumns = @JoinColumn(name="event_id"),
				inverseJoinColumns = @JoinColumn(name="person_id"))
	private List<Person> participants;
	
	public Event() {
	}

	public Event(String name, String description, LocalDate startDate, LocalTime startTime, String address,
			Boolean isModerated, Person creator, List<Photo> photos, List<Comment> comments) {
		super();
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.startTime = startTime;
		this.address = address;
		this.isModerated = isModerated;
		this.creator = creator;
		this.photos = photos;
		this.comments = comments;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime; 
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getIsModerated() {
		return isModerated;
	}

	public void setIsModerated(Boolean isModerated) {
		this.isModerated = isModerated;
	}

	public Person getCreator() {
		return creator;
	}

	public void setCreator(Person creator) {
		this.creator = creator;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	
	
	public List<Person> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Person> participants) {
		this.participants = participants;
	}

	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", name=" + name + ", description=" + description + ", startDate="
				+ startDate + ", localTime=" + startTime + ", address=" + address + ", isModerated=" + isModerated
				+ "]";
	}

	public boolean isNotParticipant() {
		if (participants == null && participants.size() == 0)
			return true;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PersonDetails personDetails = (PersonDetails) auth.getPrincipal();
		Person person = personDetails.getPerson();
		
		for (Person participant : participants) {
			if (participant.getPersonId() == person.getPersonId()) {
				return false;
			}
		}
		return true;
	}
	
}
