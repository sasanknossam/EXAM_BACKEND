package com.esa.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esa.entity.User;
import com.esa.repository.ExamRepository;
import com.esa.repository.RoomRepository;
import com.esa.repository.SeatingArrangementRepository;
import com.esa.repository.StudentRepository;
import com.esa.repository.UserRepository;
import com.esa.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	 @Autowired
	    private UserRepository userRepository;
	    
	 @Override
	    public boolean authenticateUser(String username, String password, String role) {
	        if (role.equalsIgnoreCase("Admin")) {
	            // Validate admin credentials
	            if (username.equals("nossamsasank@gmail.com") && password.equals("sasi@991")) {
	                return true;
	            }
	        } else if (role.equalsIgnoreCase("Student")) {
	            // Validate student credentials
	            if (username.matches("\\d{5}-[A-Z]{2}-\\d{3}") && password.matches("\\d{4}-\\d{2}-\\d{2}")) {
	                return true; // Replace with database lookup if needed
	            }
	        }
	        return false;
	    }
	
	
}
