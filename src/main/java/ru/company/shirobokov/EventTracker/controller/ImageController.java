package ru.company.shirobokov.EventTracker.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.company.shirobokov.EventTracker.model.Photo;
import ru.company.shirobokov.EventTracker.service.PhotoService;

@RestController
public class ImageController {
	
	private final PhotoService photoService;
	
	@Autowired
	public ImageController(PhotoService photoService) {
		this.photoService=photoService;
	}
	
	
	@GetMapping("/image/{imageName}")
	public byte[] getImage(@PathVariable("imageName") String imageName) throws IOException {
		
		System.out.println(imageName);
		
		Photo photo = photoService.getPhoto(imageName);
    	
        Path imagePath = Paths.get(photo.getPath());
		
		if (Files.exists(imagePath)) {
		byte[] imageBytes = Files.readAllBytes(imagePath);
		return imageBytes;
		} else {
		return null; // Handle missing images
		}
	}
}
