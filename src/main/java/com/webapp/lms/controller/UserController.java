package com.webapp.lms.controller;

import com.webapp.lms.model.User;
import com.webapp.lms.security.JwtUtil;
import com.webapp.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/test")
    public String testApi() {
        return "API is working!";
    }


    // Sign-up (User Registration)
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
    	//debugging
    	System.out.println("Signup request received: " + user);
    	
        Map<String, String> response = new HashMap<>();
        try {
            userService.registerUser(user);
            response.put("message", "User registered successfully!");
            return ResponseEntity.ok(response);  // Return JSON response
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response); // Return JSON error response
        }
    }

    // Login
//    @PostMapping("/login")
//    public String loginUser(@RequestParam String userName, @RequestParam String password) {
//        boolean isValid = userService.validateUser(userName, password);
//        if (isValid) {
//            return "Login successful!";
//        } else {
//            return "Invalid username or password!";
//        }
//    }
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        boolean isValid = userService.validateUser(loginRequest.getUserName(), loginRequest.getPassword());

        if (isValid) {
        	String token = jwtUtil.generateToken(loginRequest.getUserName());
        	
        	Map<String, String> response = new HashMap<>();
            response.put("message", "Login successful!");
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Invalid username or password!"));
        }
    }

    // Get user details by username
    @GetMapping("/{userName}")
    public Optional<User> getUserByUserName(@PathVariable String userName) {
        return userService.findUserByUserName(userName);
    }
}
