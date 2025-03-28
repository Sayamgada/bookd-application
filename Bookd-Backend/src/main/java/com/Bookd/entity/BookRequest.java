package com.Bookd.entity;

import java.util.UUID;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class BookRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID requestId;
	
	@NotNull
	private UUID uploadId;
	
	@NotNull
	private UUID userId;
	
	@NotNull
	private double offeredPrice;
	
	@Nullable
	private String requestStatus;

	public UUID getRequestId() {
		return requestId;
	}

	public void setRequestId(UUID requestId) {
		this.requestId = requestId;
	}

	public UUID getUploadId() {
		return uploadId;
	}

	public void setUploadId(UUID uploadId) {
		this.uploadId = uploadId;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public double getOfferedPrice() {
		return offeredPrice;
	}

	public void setOfferedPrice(double offeredPrice) {
		this.offeredPrice = offeredPrice;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}
	
	

}
