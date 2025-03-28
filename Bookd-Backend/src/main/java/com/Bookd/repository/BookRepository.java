package com.Bookd.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.Bookd.entity.Books;

@CrossOrigin(origins = "*")
@Repository
public interface BookRepository extends JpaRepository<Books, UUID> {
	
	boolean existsByBookName(String name);
	
	List<Books> findByGenreId(UUID genreId);

	long countByApproveStatus(int approveStatus);
	
	List<Books> findByApproveStatus(int approveStatus);

	Optional<Books> findByBooksId(UUID bookId);
}
