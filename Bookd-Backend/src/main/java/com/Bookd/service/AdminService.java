package com.Bookd.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bookd.entity.Admin;
import com.Bookd.entity.User;
import com.Bookd.repository.AdminRepository;

@Service
public class AdminService {
	
	@Autowired
	AdminRepository adminRepository;
	
	public boolean checkAdminExists(String name)
	{
		return adminRepository.findByAdminName(name).isPresent();
	}


	public Admin registerAdmin(Admin admin) {
		// TODO Auto-generated method stub
		return adminRepository.save(admin);
	}


	public Optional<Admin> getAdminByCredentials(String name, String password) {
        return adminRepository.findByAdminNameAndAdminPassword(name, password);
    }

}
