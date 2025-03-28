package com.Bookd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.Bookd.entity.User;
import com.Bookd.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public boolean checkUserExists(String email, String contact)
	{
		return userRepository.findByUserEmail(email).isPresent() || userRepository.findByUserContact(contact).isPresent();
	}
	
	public User registerUser(User user)
	{
		return userRepository.save(user);
	}
	
	
	public boolean checkUserCredentials(String email, String password)
	{
		return userRepository.findByUserEmailAndUserPassword(email, password).isPresent();
	}

	public Optional<User> getUserByCredentials(String email, String password) {
        return userRepository.findByUserEmailAndUserPassword(email, password);
    }
	
	public long getTotalUsers() {
        return userRepository.count();
    }
	
	public List<User> getUserData()
	{
		return userRepository.findAll();
	}
	
}
