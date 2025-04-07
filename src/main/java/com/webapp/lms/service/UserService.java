package com.webapp.lms.service;

import com.webapp.lms.model.User;
import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    boolean validateUser(String userName, String password);
    Optional<User> findUserByUserName(String userName);
    Optional<User> findUserByEmailId(String emailId);
}
