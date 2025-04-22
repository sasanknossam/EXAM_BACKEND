package com.esa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.esa.entity.User;
import com.esa.repository.UserRepository;
@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Initialize Admin user if not already present
        if (!userRepository.existsByUsername("nossamsasank@gmail.com")) {
            User admin = new User();
            admin.setUsername("nossamsasank@gmail.com");
            admin.setPassword("sasi@991"); // Set the desired default password
            admin.setRole("ADMIN");
            userRepository.save(admin);
        }
    }
}
