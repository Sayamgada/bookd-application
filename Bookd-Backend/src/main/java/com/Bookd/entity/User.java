package com.Bookd.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID userId;
	
	@NotBlank
	@NotNull
	private String userName;
	
	@NotBlank
	@NotNull
	private String userPassword;
	
	@NotBlank
	@NotNull
	private String userContact;
	
	@NotBlank
	@NotNull
	private String userEmail;
	
	@NotBlank
	@NotNull
	private String userLocation;

	public UUID getUser_id() {
		return userId;
	}

	public void setUser_id(UUID user_id) {
		this.userId = user_id;
	}

	public String getUser_name() {
		return userName;
	}

	public void setUser_name(String user_name) {
		this.userName = user_name;
	}

	public String getUser_password() {
		return userPassword;
	}

	public void setUser_password(String user_password) {
		this.userPassword = user_password;
	}

	public String getUser_contact() {
		return userContact;
	}

	public void setUser_contact(String user_contact) {
		this.userContact = user_contact;
	}

	public String getUser_email() {
		return userEmail;
	}

	public void setUser_email(String user_email) {
		this.userEmail = user_email;
	}

	public String getUser_location() {
		return userLocation;
	}

	public void setUser_location(String user_location) {
		this.userLocation = user_location;
	}
	
	
}
