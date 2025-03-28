package com.Bookd.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Bookd.entity.Genre;
import com.Bookd.service.GenreService;

@RestController
@CrossOrigin("*")
@RequestMapping("/genres")
public class GenreController {
	
	
	@Autowired
    GenreService genreService;


    @PostMapping("/add")
    public ResponseEntity<?> addGenre(@RequestParam String name, @RequestParam String description,MultipartFile image) {
        
    	if (name.isEmpty() || description.isEmpty() || image.isEmpty()) 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All fields are required.");
        }

        try 
        {
            Genre genre = genreService.addGenre(name, description, image);
            return ResponseEntity.ok("Genre added successfully!");
        } 
        catch (IOException e) 	
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        } 
        catch (IllegalArgumentException e) 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( e.getMessage());
        }
    }
    
    
    @GetMapping("/allGenre")
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }
    
    
    @GetMapping("{genreId}")
    public ResponseEntity<Genre> getGenreById(@PathVariable UUID genreId) {
        Genre genre = genreService.getGenreById(genreId);
        if (genre != null) {
            return ResponseEntity.ok(genre);
        }
        return ResponseEntity.notFound().build();
    }
	
    
    @GetMapping("/with-books")
    public ResponseEntity<List<Map<String, Object>>> getGenresWithBooks() {
        return ResponseEntity.ok(genreService.getGenresWithBooks());
    }
    

}
