package com.Bookd.entity;

import java.util.UUID;

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
public class Genre {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID genreId;
	
	@NotBlank
	@NotNull
	private String genreName;
	
	@NotBlank
	@NotNull
	@Lob
	private String genreDescription;
	
	@NotBlank
	@NotNull
	private String imagePath;

	public UUID getGenreId() {
		return genreId;
	}

	public void setGenreId(UUID genreId) {
		this.genreId = genreId;
	}

	public String getGenreName() {
		return genreName;
	}

	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	public String getGenreDescription() {
		return genreDescription;
	}

	public void setGenreDescription(String genreDescription) {
		this.genreDescription = genreDescription;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	
	
}
