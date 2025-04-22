package com.esa.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esa.model.LoginDTO;
import com.esa.service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginDTO) {
        Map<String, String> response = new HashMap<>();
        String role = loginDTO.getRole();

        if (role == null || (!role.equalsIgnoreCase("Admin") && !role.equalsIgnoreCase("Student"))) {
            response.put("message", "Invalid role provided");
            return ResponseEntity.badRequest().body(response);
        }

        if (role.equalsIgnoreCase("Admin")) {
            // Admin login logic
            if (loginDTO.getUsername().equals("nossamsasank@gmail.com") &&
                loginDTO.getPassword().equals("sasi@991")) {
                response.put("message", "Admin login successful");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Invalid admin credentials");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } else if (role.equalsIgnoreCase("Student")) {
            // Student login logic
            if (validateStudent(loginDTO.getUsername(), loginDTO.getPassword())) {
                response.put("message", "Student login successful");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Invalid student credentials");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        }

        response.put("message", "Unhandled role");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private boolean validateStudent(String username, String password) {
        // Replace with actual student validation logic
        return username.startsWith("22022") && password != null;
    }
    


}
