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
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Bookd.entity.BookUpload;
import com.Bookd.entity.Books;
import com.Bookd.entity.Genre;
import com.Bookd.entity.User;
import com.Bookd.repository.BookRepository;
import com.Bookd.repository.BookUploadRepository;
import com.Bookd.repository.GenreRepository;
import com.Bookd.repository.UserRepository;

@Service
public class GenreService {

	@Autowired
	GenreRepository genreRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	BookUploadRepository bookUploadRepository;
	
	@Autowired
	UserRepository userRepository;
	
	private static final String UPLOAD_DIR = "uploads/genre/";

    public Genre addGenre(String name, String description, MultipartFile image) throws IOException 
    {
        if (genreRepository.existsByGenreName(name)) 
        {
            throw new IllegalArgumentException("Genre with this name already exists.");
        }

        Genre genre = new Genre();
        genre.setGenreName(name);
        genre.setGenreDescription(description);

        String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
        Path imagePath = Paths.get(UPLOAD_DIR, fileName);

        Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
        genre.setImagePath(imagePath.toString());

        return genreRepository.save(genre);
    }
    
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }
    
    
    public Genre getGenreById(UUID genreId) {
        return genreRepository.findById(genreId).orElse(null);
    }
    
//    genre.html
    public List<Map<String, Object>> getGenresWithBooks() {
        List<Genre> genres = genreRepository.findAll();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Genre genre : genres) {
            List<Books> books = bookRepository.findByGenreId(genre.getGenreId());
            
            Map<String, Object> genreData = new HashMap<>();
            genreData.put("genreName", genre.getGenreName());
            genreData.put("books", books);

            response.add(genreData);
        }

        return response;
    }
   


}
