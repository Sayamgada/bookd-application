package com.Bookd.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bookd.entity.BookRequest;
import com.Bookd.entity.BookUpload;
import com.Bookd.entity.Books;
import com.Bookd.entity.TransactionHistory;
import com.Bookd.entity.User;
import com.Bookd.repository.BookRepository;
import com.Bookd.repository.BookRequestRepository;
import com.Bookd.repository.BookUploadRepository;
import com.Bookd.repository.TransactionHistoryRepository;
import com.Bookd.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class BookRequestService {

	@Autowired
	BookRequestRepository bookRequestRepository;
	
	@Autowired
	private BookUploadRepository bookUploadRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository booksRepository;

	@Autowired
	TransactionHistoryRepository transactionHistoryRepository;
	
	
	public List<Map<String, Object>> getUserRequests(UUID userId) {

	    List<BookRequest> requestData = bookRequestRepository.findByUserId(userId);

	    return requestData.stream().map(data -> {
	        Map<String, Object> requestMap = new HashMap<>();
	        requestMap.put("requestId", data.getRequestId());
	        requestMap.put("requestStatus", data.getRequestStatus());
	        requestMap.put("offeredAmount", data.getOfferedPrice());

	        BookUpload bookUpload = bookUploadRepository.findById(data.getUploadId())
	                                    .orElseThrow(() -> new RuntimeException("BookUpload not found"));

	        User uploader = userRepository.findById(bookUpload.getUserId())
	                            .orElseThrow(() -> new RuntimeException("Uploader not found"));
	        requestMap.put("ownerName", uploader.getUser_name());
	        requestMap.put("ownerContact", uploader.getUser_contact());
	        requestMap.put("ownerEmail", uploader.getUser_email());

	        Books book = booksRepository.findById(bookUpload.getBookId())
	                        .orElseThrow(() -> new RuntimeException("Book not found"));
	        requestMap.put("bookName", book.getBookName());

	        requestMap.put("actualPrice", bookUpload.getBookPrice());

	        return requestMap;
	    }).collect(Collectors.toList());
	}

	@Transactional
    public void approveRequest(UUID requestId) {
        BookRequest approvedRequest = bookRequestRepository.findById(requestId)
            .orElseThrow(() -> new RuntimeException("Request not found"));

        UUID uploadId = approvedRequest.getUploadId();

        approvedRequest.setRequestStatus("confirmed");
        bookRequestRepository.save(approvedRequest);

        List<BookRequest> otherRequests = bookRequestRepository.findByUploadId(uploadId);
        for (BookRequest request : otherRequests) {
            if (!request.getRequestId().equals(requestId)) {
                request.setRequestStatus("declined");
                bookRequestRepository.save(request);
            }
        }

        BookUpload bookUpload = bookUploadRepository.findById(uploadId)
            .orElseThrow(() -> new RuntimeException("Book upload not found"));
        bookUpload.setAvailableStatus(0);
        bookUploadRepository.save(bookUpload);

        TransactionHistory transaction = new TransactionHistory();
        transaction.setRequestId(requestId);
        transaction.setTransactionDate(LocalDate.now());
        transactionHistoryRepository.save(transaction);
    }



}
