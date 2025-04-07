package com.webapp.lms.service;

import com.webapp.lms.model.User;

import com.webapp.lms.repository.UserRepository;
import com.webapp.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder  passwordEncoder;  // Injecting password encoder

    @Override
    public User registerUser(User user) {
        if (userRepository.existsByUserName(user.getUserName())) {
            throw new RuntimeException("Username already exists!");
        }
        if (userRepository.existsByEmailId(user.getEmailId())) {
            throw new RuntimeException("Email ID already registered!");
        }

        // ✅ Hash password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
    
//    @Override
//    public User registerUser(User user) {
//        if (userRepository.existsByUserName(user.getUserName())) {
//            throw new RuntimeException("Username already exists!");
//        }
//        if (userRepository.existsByEmailId(user.getEmailId())) {
//            throw new RuntimeException("Email ID already registered!");
//        }
//        return userRepository.save(user);
//    }

//    @Override
//    public boolean validateUser(String userName, String password) {
//        Optional<User> user = userRepository.findByUserName(userName);
//        return user.isPresent() && user.get().getPassword().equals(password);
//    }


    public boolean validateUser(String userName, String password) {
        Optional<User> user = userRepository.findByUserName(userName);

        // Compare the entered password with the hashed password in DB
        return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
    }

    @Override
    public Optional<User> findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Optional<User> findUserByEmailId(String emailId) {
        return userRepository.findByEmailId(emailId);
    }
}
