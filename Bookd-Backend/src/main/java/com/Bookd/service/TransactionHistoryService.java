package com.Bookd.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bookd.entity.BookRequest;
import com.Bookd.entity.BookUpload;
import com.Bookd.entity.TransactionHistory;
import com.Bookd.repository.BookRequestRepository;
import com.Bookd.repository.BookUploadRepository;
import com.Bookd.repository.TransactionHistoryRepository;

@Service
public class TransactionHistoryService {
	
	@Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    private BookRequestRepository bookRequestRepository;

    @Autowired
    private BookUploadRepository bookUploadRepository;

    
    public long getTotalTransactionCount()
    {
    	return transactionHistoryRepository.count();
    }
    
    public List<TransactionHistory> getAllTransactions()
    {
    	return transactionHistoryRepository.findAll();
    }
    
}
