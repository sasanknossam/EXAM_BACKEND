package com.esa.service;

import java.util.Optional;

import com.esa.entity.User;

public interface UserService {
	
	boolean authenticateUser(String username, String password, String role);
//	
//	Optional<User> findByUsername(String username);
}
