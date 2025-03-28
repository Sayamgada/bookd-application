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

import com.Bookd.entity.Books;
import com.Bookd.service.BookService;

@CrossOrigin("*")
@RestController
@RequestMapping("/books")
public class BookController {
	
	@Autowired
    private BookService bookService;

    @PostMapping("/addBooks")
    public ResponseEntity<?> addBooks(@RequestParam String name, @RequestParam String authorName, @RequestParam String description, @RequestParam UUID genre, @RequestParam MultipartFile image) 
    { 
        if (name.isEmpty() || description.isEmpty() || image.isEmpty()) 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All fields are required.");
        }

        try 
        {
            bookService.addGenre(name, description, authorName, genre, image);
            return ResponseEntity.ok(Collections.singletonMap("message", "Book added successfully!"));
        } 
        catch (IOException e) 
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        } 
        catch (IllegalArgumentException e) 
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    
    
    @GetMapping("/genre/{genreId}")
    public ResponseEntity<List<Map<String, Object>>> getLiveBooksByGenreId(@PathVariable UUID genreId) {
        List<Map<String, Object>> books = bookService.getLiveBooksByGenreId(genreId);
        return books.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(books);
    }		

    
    @GetMapping("/{bookId}")
    public ResponseEntity<Books> getBookById(@PathVariable UUID bookId) {
        return bookService.getBookById(bookId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
