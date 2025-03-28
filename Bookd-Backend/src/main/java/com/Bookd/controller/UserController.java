package com.Bookd.controller;

import java.util.Collection;
import java.util.*;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Bookd.entity.User;
import com.Bookd.repository.UserRepository;
import com.Bookd.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/check")
	public ResponseEntity<?> checkUserExists(@RequestParam String email, @RequestParam String contact)
	{
		boolean user_exists = userService.checkUserExists(email, contact);
		
		if(user_exists)
		{
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists.");
		}
		return ResponseEntity.ok(Collections.singletonMap("message", "User registered successfully"));
	}
	
	@GetMapping("/checklogin")
	public ResponseEntity<?> checkLoginCredentials(@RequestParam String email, @RequestParam String password) {
	    Optional<User> user = userService.getUserByCredentials(email, password);

	    if (user.isPresent()) {
	        return ResponseEntity.ok(Collections.singletonMap("user_id", user.get().getUser_id()));
	    }

	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User does not exist with this email or password.");
	}

	
	
	@PostMapping
	public ResponseEntity<?> registerUser(@RequestBody User user)
	{
		if(userService.checkUserExists(user.getUser_email(), user.getUser_contact()))
		{
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists.");
		}
		
		User new_user = userService.registerUser(user);
		return ResponseEntity.ok(Collections.singletonMap("user_id", new_user.getUser_id()));
	}
	
	@GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID userId) {
        return userRepository.findById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
