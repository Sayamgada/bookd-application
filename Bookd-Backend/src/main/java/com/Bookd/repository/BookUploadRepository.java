package com.Bookd.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.Bookd.entity.BookUpload;

@Repository
@CrossOrigin("*")
public interface BookUploadRepository extends JpaRepository<BookUpload, UUID> {

	long countByAvailableStatus(int availableStatus);
	
	List<BookUpload> findByAvailableStatus(int availableStatus);
	
	List<BookUpload> findByUserId(UUID userId);
	
	List<BookUpload> findByBookIdInAndAvailableStatus(List<UUID> bookIds, int availableStatus);
	
	
}
