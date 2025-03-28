package com.Bookd.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.Bookd.entity.User;

@CrossOrigin(origins = "*")
@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
	
	List<User> findByUserId(UUID userID);

	Optional<User> findByUserEmail(String email);
	Optional<User> findByUserContact(String contact);
	Optional<User> findByUserPassword(String password);
	
	Optional<User> findByUserEmailAndUserPassword(String email, String password);
	
	String findUserLocationByUserId(UUID userId);
	
	List<User> findUserLocationByUserIdIn(List<UUID> userIds);

	
}
