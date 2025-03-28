package com.Bookd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Bookd.entity.BookRequest;
import com.Bookd.entity.BookUpload;
import com.Bookd.entity.Books;
import com.Bookd.entity.Genre;
import com.Bookd.entity.User;
import com.Bookd.repository.BookRepository;
import com.Bookd.repository.BookRequestRepository;
import com.Bookd.repository.BookUploadRepository;
import com.Bookd.repository.GenreRepository;
import com.Bookd.repository.UserRepository;
import com.Bookd.service.BookRequestService;

@RestController
@CrossOrigin("*")
@RequestMapping("/bookrequest")
public class BookRequestController {
	
	@Autowired
	BookRequestRepository bookRequestRepository;
	
	@Autowired
	BookRequestService bookRequestService;
	
	@Autowired
	BookUploadRepository bookUploadRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired 
	GenreRepository genreRepository;
	
	@Autowired
	UserRepository userRepository;
	
	
	@PostMapping("/createRequest")
    public ResponseEntity<String> createRequest(@RequestBody BookRequest bookRequest) {
        bookRequest.setRequestStatus("Pending");
        bookRequestRepository.save(bookRequest);
        return ResponseEntity.ok("Book request created successfully.");
    }
	
	@GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserRequests(@PathVariable UUID userId) {
        return ResponseEntity.ok(bookRequestService.getUserRequests(userId));
    }
	
	@GetMapping("/upload/{uploadId}")
	public List<BookRequest> getRequestsByUploadId(@PathVariable List<UUID> uploadId) {
	    return bookRequestRepository.findByUploadIdInAndRequestStatus(uploadId, "pending");
	}

	 
	 @GetMapping("/book-info/{uploadId}")
	 public ResponseEntity<?> getBookInfo(@PathVariable UUID uploadId) {
	     BookUpload bookUpload = bookUploadRepository.findById(uploadId)
	             .orElseThrow(() -> new RuntimeException("BookUpload not found"));

	     Books book = bookRepository.findById(bookUpload.getBookId())
	             .orElseThrow(() -> new RuntimeException("Book not found"));

	     Genre genre = genreRepository.findById(book.getGenreId())
	             .orElseThrow(() -> new RuntimeException("Genre not found"));

	     Map<String, Object> bookInfo = new HashMap<>();
	     bookInfo.put("bookImage", book.getBookImage());
	     bookInfo.put("bookTitle", book.getBookName());
	     bookInfo.put("bookPrice", bookUpload.getBookPrice());
	     bookInfo.put("bookCondition", bookUpload.getBookCondition());
	     bookInfo.put("bookGenre", genre.getGenreName());
	     bookInfo.put("bookAuthor", book.getBookAuthor());

	     return ResponseEntity.ok(bookInfo);
	 }

	 @PatchMapping("/declineRequest/{requestId}")
	 public ResponseEntity<?> declineRequest(@PathVariable UUID requestId) {
	     BookRequest request = bookRequestRepository.findById(requestId)
	             .orElseThrow(() -> new RuntimeException("Request not found"));

	     request.setRequestStatus("declined");
	     bookRequestRepository.save(request);

	     return ResponseEntity.ok("Request declined successfully.");
	 }

	 @PatchMapping("/approve/{requestId}")
	 public ResponseEntity<?> approveRequest(@PathVariable UUID requestId) {
	     try {
	         bookRequestService.approveRequest(requestId);
	         return ResponseEntity.ok().build();
	     } catch (Exception e) {
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error approving request.");
	     }
	 }
	 
	 @GetMapping("/{uploadId}")
	 public List<Map<String, Object>> getRequestData(@PathVariable UUID uploadId)
	 {
		 List<Map<String, Object>> response = new ArrayList<>();
//		 List<BookRequest> book = bookRequestRepository.findByUploadIdAndRequestStatus(uploadId, "confirmed");
		 List<BookRequest> request = bookRequestRepository.findByUploadIdAndRequestStatus(uploadId, "confirmed");
		 UUID userId = request.get(0).getUserId();
		 
		 List<User> user = userRepository.findByUserId(userId);
		 
		 Map<String, Object> data = new HashMap<>();
		 data.put("requestId", request.get(0).getRequestId());
		 data.put("bookPrice", request.get(0).getOfferedPrice());
		 data.put("userId", user.get(0).getUser_id());
		 data.put("userName", user.get(0).getUser_name());
		 data.put("userLocation", user.get(0).getUser_location());
		 data.put("userContact", user.get(0).getUser_contact());
		 data.put("userEmail", user.get(0).getUser_email());
		 
		 response.add(data);
		 return response;
		 
	 }


}
