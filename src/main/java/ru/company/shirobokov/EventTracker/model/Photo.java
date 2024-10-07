package ru.company.shirobokov.EventTracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "photo")
public class Photo {
	@Id
	@Column(name = "photo_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int photoId;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "path")
	private String path;

	@ManyToOne
	@JoinColumn(name = "event_id", referencedColumnName = "event_id")
	private Event event;

	public Photo() {
	}

	public Photo(String fileName, String path, Event event) {
		super();
		this.fileName = fileName;
		this.path = path;
		this.event = event;
	}

	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@Override
	public String toString() {
		return "Photo [photoId=" + photoId + ", fileName=" + fileName + ", path=" + path + "]";
	}

}
