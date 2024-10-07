package ru.company.shirobokov.EventTracker.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "person")
public class Person {
	@Id
	@Column(name = "person_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int personId;

	@Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов")
	@Column(name = "first_name")
	private String firstName;

	@Size(min = 2, max = 100, message = "Фималия должна быть от 2 до 100 символов")
	@Column(name = "last_name")
	private String lastName;

	@Size(min = 2, max = 100, message = "Отчество должно быть от 2 до 100 символов")
	@Column(name = "father_name")
	private String fatherName;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Size(min = 2, max = 100, message = "Логин должен быть от 2 до 100 символов")
	@Column(name = "username")
	private String username;

	@Size(min = 2, max = 100, message = "Пароль должнен быть от 2 до 100 символов")
	@Column(name = "password")
	private String password;

	@Column(name = "role")
	private String role;

	@OneToMany(mappedBy = "creator")
	private List<Event> events;

	@OneToMany(mappedBy = "person")
	private List<Comment> comments;
	
	@ManyToMany(mappedBy = "participants")
	private List<Event> goToEvents;
	
	public Person() {
	}

	public Person(String firstName, String lastName, String fatherName, String phoneNumber, String username,
			String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.fatherName = fatherName;
		this.phoneNumber = phoneNumber;
		this.username = username;
		this.password = password;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Event> getGoToEvents() {
		return goToEvents;
	}

	public void setGoToEvents(List<Event> goToEvents) {
		this.goToEvents = goToEvents;
	}

	@Override
	public String toString() {
		return "Person [id=" + personId + ", firstName=" + firstName + ", lastName=" + lastName + ", fatherName="
				+ fatherName + ", phoneNumber=" + phoneNumber + ", username=" + username + ", password=" + password
				+ "]";
	}

}
