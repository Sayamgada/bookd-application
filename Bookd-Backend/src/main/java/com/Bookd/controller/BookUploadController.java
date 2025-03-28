package com.Bookd.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Bookd.entity.BookUpload;
import com.Bookd.entity.Books;
import com.Bookd.entity.Genre;
import com.Bookd.service.BookService;
import com.Bookd.service.BookUploadService;
import com.Bookd.service.GenreService;

@CrossOrigin("*")
@RestController
@RequestMapping("/bookupload")
public class BookUploadController {
	
	@Autowired
	BookUploadService bookUploadService;
	
	@Autowired
	BookService bookService;
	
	@Autowired
	GenreService genreService;
	
//	get all books for book upload form drop down
	@GetMapping("/allBooks")
    public List<Books> getAllBooks() {
        return bookService.getApprovedBooks();
    }
	
//	posting data
	@PostMapping("/uploadBook")	
    public ResponseEntity<?> uploadBook(@RequestBody BookUpload bookUpload) {
        BookUpload savedBook = bookUploadService.saveBookUpload(bookUpload);

        if (savedBook != null) {
            return ResponseEntity.ok("{\"status\":\"success\"}");
        } else {
            return ResponseEntity.status(500).body("{\"status\":\"error\"}");
        }
    }
	
	
	@GetMapping("/user/{userId}")
    public List<Map<String, Object>> getUserUploads(@PathVariable UUID userId) {
        return bookUploadService.getUploadsByUserId(userId);
    }
	
	@GetMapping("/getBookData/{uploadId}")
    public ResponseEntity<Map<String, Object>> getBookData(@PathVariable UUID uploadId) {
        Map<String, Object> bookData = bookUploadService.getBookDataByUploadId(uploadId);
        return ResponseEntity.ok(bookData);
    }	
	
//	@GetMapping("/allLiveBooks/{genreId}")
//    public ResponseEntity<List<Map<String, Object>>> getLiveBooksByGenreId(@PathVariable UUID genreId) {
//        List<Map<String, Object>> books = bookService.getLiveBooksByGenreId(genreId);
//        return books.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(books);
//    }
	
	@GetMapping("/allLiveBooks/{genreId}")
	public ResponseEntity<?> getAllLiveBooks(@PathVariable UUID genreId) {
		List<Map<String, Object>> books = bookService.getLiveBooksByGenreId(genreId);
	    return ResponseEntity.ok(books != null ? books : Collections.emptyList());
	}


}
