package com.Bookd.entity;

import java.util.UUID;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Books {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID booksId;
	
	@NotBlank
	@NotNull
	private String bookName;
	
	@NotBlank
	@NotNull
	@Lob
	private String bookDesc;
	
	@NotBlank
	@NotNull
	private String bookAuthor;
	
	@NotBlank
	@NotNull
	private String bookImage;
	
	@NotNull
	private UUID genreId;
	
	@NotNull
	private int approveStatus;
	
	@Nullable
	private String approvedBy;

	public UUID getBooksId() {
		return booksId;
	}

	public void setBooksId(UUID booksId) {
		this.booksId = booksId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookDesc() {
		return bookDesc;
	}

	public void setBookDesc(String bookDesc) {
		this.bookDesc = bookDesc;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public String getBookImage() {
		return bookImage;
	}

	public void setBookImage(String bookImage) {
		this.bookImage = bookImage;
	}

	public UUID getGenreId() {
		return genreId;
	}

	public void setGenreId(UUID genreId) {
		this.genreId = genreId;
	}

	public int getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(int approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	
	
}
