package com.Bookd.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Bookd.entity.BookRequest;

@Repository
public interface BookRequestRepository extends JpaRepository<BookRequest, UUID> {
	
	List<BookRequest> findByUserId(UUID userId);
	
	List<BookRequest> findByUploadIdIn(List<UUID> uploadIds);

	List<BookRequest> findByUploadIdInAndRequestStatus(List<UUID> uploadId, String requestStatus);

	List<BookRequest> findByUploadIdAndRequestStatus(UUID uploadId, String requestStatus);
	
	List<BookRequest> findByRequestId(UUID requestId);
	
	List<BookRequest> findByUploadId(UUID uploadId);
}
