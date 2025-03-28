package com.Bookd.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
	
@Entity
@Data
public class BookUpload {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID uploadID;
	
	@NotNull
	private UUID bookId;
	
	@NotNull
	private UUID userId;
	
	@NotBlank
	@NotNull
	private String bookCondition;
	
	@NotNull
	private double bookPrice;
	
	@NotNull
	private int availableStatus;

	public UUID getUploadID() {
		return uploadID;
	}

	public void setUploadID(UUID uploadID) {
		this.uploadID = uploadID;
	}

	public UUID getBookId() {
		return bookId;
	}

	public void setBookId(UUID bookId) {
		this.bookId = bookId;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getBookCondition() {
		return bookCondition;
	}

	public void setBookCondition(String bookCondition) {
		this.bookCondition = bookCondition;
	}

	public double getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(double bookPrice) {
		this.bookPrice = bookPrice;
	}

	public int getAvailableStatus() {
		return availableStatus;
	}

	public void setAvailableStatus(int availableStatus) {
		this.availableStatus = availableStatus;
	}
	
	
	

}
