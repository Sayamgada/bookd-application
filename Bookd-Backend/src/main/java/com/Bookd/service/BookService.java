package com.Bookd.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Bookd.repository.BookRepository;
import com.Bookd.repository.BookUploadRepository;
import com.Bookd.repository.UserRepository;
import com.Bookd.entity.BookUpload;
import com.Bookd.entity.Books;
import com.Bookd.entity.User;

@Service
public class BookService {
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	BookUploadRepository bookUploadRepository;
	
	@Autowired
	UserRepository userRepository;
	
	private static final String UPLOAD_DIR = "uploads/books/";
	
	public Books addGenre(String name, String description, String author, UUID genre, MultipartFile image) throws IOException 
	{
        if (bookRepository.existsByBookName(name)) 
        {
            throw new IllegalArgumentException("Book with this name already exists.");
        }
        
        int approved_status = 0;
        String approved_by = null;

        Books books = new Books();
        books.setBookName(name);
        books.setBookDesc(description);
        books.setBookAuthor(author);
        books.setGenreId(genre);
        books.setApproveStatus(approved_status);
        books.setApprovedBy(approved_by);

        // Save image file
        String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
        Path imagePath = Paths.get(UPLOAD_DIR, fileName);

        Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
        books.setBookImage(imagePath.toString());

        return bookRepository.save(books);
    }

	public long countPendingRequests() {
        return bookRepository.countByApproveStatus(0);
    }
	
	public List<Map<String, Object>> getLiveBooksByGenreId(UUID genreId) {
        List<Books> books = bookRepository.findByGenreId(genreId);

        List<UUID> bookIds = books.stream()
                                  .map(Books::getBooksId)
                                  .toList();

        List<BookUpload> liveUploads = bookUploadRepository.findByBookIdInAndAvailableStatus(bookIds, 1);
        
        List<UUID> userIds = liveUploads.stream()
                .map(BookUpload::getUserId)
                .toList();

        List<User> userData = userRepository.findUserLocationByUserIdIn(userIds);
        

        List<Map<String, Object>> response = new ArrayList<>();
        for (BookUpload upload : liveUploads) {
            Books book = books.stream()
                              .filter(b -> b.getBooksId().equals(upload.getBookId()))
                              .findFirst()
                              .orElse(null);
            
            String userLocation = userData.stream()
                    .filter(user -> user.getUser_id().equals(upload.getUserId()))
                    .map(User::getUser_location)
                    .findFirst()
                    .orElse("Unknown Location");

            if (book != null) {
                Map<String, Object> bookData = new HashMap<>();
                bookData.put("booksId", book.getBooksId());
                bookData.put("bookName", book.getBookName());
                bookData.put("bookImage", book.getBookImage());
                bookData.put("bookPrice", upload.getBookPrice());
                bookData.put("uploadId", upload.getUploadID());
                bookData.put("userLocation", userLocation);
                response.add(bookData);
            }
        }

        return response;
    }
	
	public List<Books> getApprovedBooks() {
	    return bookRepository.findByApproveStatus(1);
	}

	public List<Books> getPendingApprovalBooks() {
	    return bookRepository.findByApproveStatus(0);
	}
	
	public Optional<Books> getBookById(UUID bookId) {
        return bookRepository.findByBooksId(bookId);
    }
	
	public boolean approveBook(UUID bookId) {
        Optional<Books> bookOpt = bookRepository.findById(bookId);

        if (bookOpt.isPresent()) 
        {
            Books book = bookOpt.get();
            if (book.getApproveStatus() == 0) 
            {
                book.setApproveStatus(1);
                bookRepository.save(book);
                return true;
            }
        }
        return false;
    }
	
	public boolean rejectBook(UUID bookId) {
        if (bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId);
            return true;
        }
        return false;
    }
}
