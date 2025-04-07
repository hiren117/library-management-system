package com.webapp.lms.repository;

import com.webapp.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom query methods
    Optional<User> findByUserName(String userName);
    
    Optional<User> findByEmailId(String emailId);
    
    boolean existsByUserName(String userName);
    
    boolean existsByEmailId(String emailId);
    
//    In Spring Data JPA, the method return type Optional<User> means that the method may or may not return a User object.
//
//    		Why Use Optional<User>?
//    		Avoids NullPointerException
//
//    		Instead of returning null, it returns an Optional object.
//    		This prevents accidental NullPointerException errors.
//    		Clear Indication that a Value May Be Absent
//
//    		If a user is found, it returns Optional.of(user).
//    		If a user is not found, it returns Optional.empty().

}
