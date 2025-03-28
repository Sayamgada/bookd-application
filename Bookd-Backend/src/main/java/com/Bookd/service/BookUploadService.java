package com.Bookd.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bookd.entity.BookUpload;
import com.Bookd.entity.Books;
import com.Bookd.entity.Genre;
import com.Bookd.entity.User;
import com.Bookd.repository.BookRepository;
import com.Bookd.repository.BookUploadRepository;
import com.Bookd.repository.GenreRepository;
import com.Bookd.repository.UserRepository;

@Service
public class BookUploadService {
	
	@Autowired
	BookUploadRepository bookUploadRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	GenreRepository genreRepository;
	
	public BookUpload saveBookUpload(BookUpload bookUpload) 
	{
        return bookUploadRepository.save(bookUpload);
    }
	
	public long getTotalLiveBooksCount() 
	{
	    return bookUploadRepository.countByAvailableStatus(1);
	}


	public List<BookUpload> getLiveBookData()
	{
		return bookUploadRepository.findByAvailableStatus(1);
	}

	
	public List<Map<String, Object>> getUploadsByUserId(UUID userId) {
        List<BookUpload> uploads = bookUploadRepository.findByUserId(userId);
        List<Map<String, Object>> response = new ArrayList<>();

        for (BookUpload upload : uploads) {
            Books book = bookRepository.findByBooksId(upload.getBookId()).orElse(null);

            Map<String, Object> uploadData = new HashMap<>();
            uploadData.put("uploadID", upload.getUploadID());
            uploadData.put("bookName", book != null ? book.getBookName() : "Unknown Book");
            uploadData.put("bookImage", book != null ? book.getBookImage() : "");
            uploadData.put("bookCondition", upload.getBookCondition());
            uploadData.put("bookPrice", upload.getBookPrice());
            uploadData.put("availableStatus", upload.getAvailableStatus());

            response.add(uploadData);
        }

        return response;
    }
	
	public Map<String, Object> getBookDataByUploadId(UUID uploadId) {
        BookUpload bookUpload = bookUploadRepository.findById(uploadId).orElseThrow(() -> new RuntimeException("Book Upload not found"));
        Books book = bookRepository.findById(bookUpload.getBookId()).orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepository.findById(bookUpload.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Genre genre = genreRepository.findById(book.getGenreId()).orElseThrow(() -> new RuntimeException("Genre not found"));

        Map<String, Object> data = new HashMap<>();
        data.put("bookName", book.getBookName());
        data.put("bookPrice", bookUpload.getBookPrice());
        data.put("bookDescription", book.getBookDesc());
        data.put("bookAuthor", book.getBookAuthor());
        data.put("bookGenre", genre.getGenreName());
        data.put("bookCondition", bookUpload.getBookCondition());
        data.put("bookImage", book.getBookImage());
        data.put("userName", user.getUser_name());
        data.put("userLocation", user.getUser_location());

        return data;
    }

}
