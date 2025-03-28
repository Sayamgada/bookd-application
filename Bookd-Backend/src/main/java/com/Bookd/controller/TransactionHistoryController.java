package com.Bookd.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.Bookd.service.TransactionHistoryService;

@RestController
public class TransactionHistoryController {
	
	@Autowired
	TransactionHistoryService transactionHistoryService;
	



}
