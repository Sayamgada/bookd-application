package com.Bookd.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.Bookd.entity.Admin;
import com.Bookd.entity.User;

@CrossOrigin(origins = "*")
@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID>{

	Optional<Admin> findByAdminName(String name);
	
	Optional<Admin> findByAdminNameAndAdminPassword(String adminName, String adminPassword);


}
