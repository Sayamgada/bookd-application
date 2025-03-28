package com.Bookd.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Bookd.entity.Admin;
import com.Bookd.entity.BookUpload;
import com.Bookd.entity.Books;
import com.Bookd.entity.Genre;
import com.Bookd.entity.TransactionHistory;
import com.Bookd.entity.User;
import com.Bookd.service.AdminService;
import com.Bookd.service.BookService;
import com.Bookd.service.BookUploadService;
import com.Bookd.service.GenreService;
import com.Bookd.service.TransactionHistoryService;
import com.Bookd.service.UserService;


@CrossOrigin("*")
@RestController
@RequestMapping("/admins")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	GenreService genreService;
	
	@Autowired
	UserService userService;
	
	
	@Autowired
	BookService bookService;
	
	@Autowired
	BookUploadService bookUploadService;
	
	@Autowired
	TransactionHistoryService transactionHistoryService;
	
	
	
	@GetMapping("/check")
	public ResponseEntity<?> checkAdminExists(@RequestParam String name)
	{
		boolean admin_exists = adminService.checkAdminExists(name);
		
		if(admin_exists)
		{
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Admin already exists.");
		}
		return ResponseEntity.ok(Collections.singletonMap("message", "Admin registered successfully"));
		
	}
	
	@GetMapping("/checklogin")
	public ResponseEntity<?> checkLoginCredentials(@RequestParam String name, @RequestParam String password) {
	    Optional<Admin> admin = adminService.getAdminByCredentials(name, password);

	    if (admin.isPresent()) {
	        return ResponseEntity.ok(Collections.singletonMap("admin_id", admin.get().getAdminId()));
	    }

	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Admin does not exist with this email or password.");
	}
	

	
	@PostMapping
	public ResponseEntity<?> registerAdmin(@RequestBody Admin admin)
	{
		if(adminService.checkAdminExists(admin.getAdminName()))
		{
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Admin already exists.");
		}
		
		Admin new_admin = adminService.registerAdmin(admin);
		return ResponseEntity.ok(Collections.singletonMap("admin_id", new_admin.getAdminId()));
	}
	
	
	
	@GetMapping("/totalUsers")
    public ResponseEntity<?> getTotalUsers() {
        long totalUsers = userService.getTotalUsers();
        return ResponseEntity.ok(totalUsers);
    }
	
	
	
    @GetMapping("/pendingRequests")
    public ResponseEntity<Long> getPendingRequestsCount() {
        long pendingCount = bookService.countPendingRequests();
        return ResponseEntity.ok(pendingCount);
    }
    
    @GetMapping("/totalLiveBooks")
    public ResponseEntity<?> getTotalLiveBooks() {
        long totalLiveBooks = bookUploadService.getTotalLiveBooksCount();
        return ResponseEntity.ok(totalLiveBooks);
    }
    
    @GetMapping("/totalTransactions")
    public ResponseEntity<?> getTotalTransaction() {
        long totalTransaction = transactionHistoryService.getTotalTransactionCount();
        return ResponseEntity.ok(totalTransaction);
    }
    
//    get data for available books section
    @GetMapping("/approvedBooks")
    public ResponseEntity<List<Books>> getApprovedBooks() 
    {
        List<Books> books = bookService.getApprovedBooks();
        return ResponseEntity.ok(books);
    }
    
    
//  get data for available books section
  @GetMapping("/pendingApprovalBooks")
  public ResponseEntity<List<Books>> getPendingApprovalBooks() 
  {
      List<Books> books = bookService.getPendingApprovalBooks();
      return ResponseEntity.ok(books);
  }
  
  @GetMapping("/userData")
  public ResponseEntity<List<User>> getUserData() 
  {		
      List<User> user = userService.getUserData();
      return ResponseEntity.ok(user);
  }
  
  
  @GetMapping("/liveBooks")
  public ResponseEntity<List<BookUpload>> getLiveBooks()
  {
	  List<BookUpload> books = bookUploadService.getLiveBookData();
	  return ResponseEntity.ok(books);
  }
    
  
  @PatchMapping("/approveBook/{bookId}")
  public ResponseEntity<String> approveBook(@PathVariable UUID bookId) {
      boolean approved = bookService.approveBook(bookId);
      return approved 
          ? ResponseEntity.ok("Book approved successfully!") 
          : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found or already approved.");
  }
  
  
  @DeleteMapping("/rejectBook/{bookId}")
  public ResponseEntity<String> rejectBook(@PathVariable UUID bookId) {
      boolean isRejected = bookService.rejectBook(bookId);
      return isRejected 
          ? ResponseEntity.ok("Book rejected successfully.") 
          : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
  }
  
  @GetMapping("/genreData")
  public ResponseEntity<List<Genre>> getGenreData() 
  {		
      List<Genre> user = genreService.getAllGenres();
      return ResponseEntity.ok(user);
  }
  
  @GetMapping("/transactionData")
  public ResponseEntity<List<TransactionHistory>> getTransactionData()
  {
	  List<TransactionHistory> transaction = transactionHistoryService.getAllTransactions();
	  return ResponseEntity.ok(transaction);	
  }
    
}
