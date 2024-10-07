package ru.company.shirobokov.EventTracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.company.shirobokov.EventTracker.model.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer> {
	
	List<Photo> findByFileName(String name);
}
