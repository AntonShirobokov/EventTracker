package ru.company.shirobokov.EventTracker.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ru.company.shirobokov.EventTracker.model.Photo;
import ru.company.shirobokov.EventTracker.repository.PhotoRepository;

@Service
public class PhotoService {
	
	private PhotoRepository photoRepository;
	
	@Autowired
	public PhotoService(PhotoRepository photoRepository) {
		this.photoRepository = photoRepository;
	}
	
	public String savePhoto(String uniqueFileName, String uploadDirectory, MultipartFile file) {
		
		Path uploadPath = Paths.get(uploadDirectory);
		
		try {
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Path filePath = uploadPath.resolve(uniqueFileName);
		
		try {
			Files.write(filePath, file.getBytes());
			file.getInputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return filePath.toString();
	}

	
	public Photo getPhoto(String name) {
		return photoRepository.findByFileName(name).get(0);
	}

}
