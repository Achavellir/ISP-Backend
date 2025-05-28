package org.isp.service;

import org.isp.model.User;
import org.isp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for checking passwords against the database
 */
@Service
public class PasswordCheckService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Check if the provided password matches the password stored in the database for a user with the given username
     * 
     * @param username the username of the user
     * @param rawPassword the password to check
     * @return true if the password matches, false otherwise or if the user doesn't exist
     */
    public boolean checkPasswordByUsername(String username, String rawPassword) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }

    /**
     * Check if the provided password matches the password stored in the database for a user with the given email
     * 
     * @param email the email of the user
     * @param rawPassword the password to check
     * @return true if the password matches, false otherwise or if the user doesn't exist
     */
    public boolean checkPasswordByEmail(String email, String rawPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }

    /**
     * Check if the provided password matches the password stored in the database for a user with the given ID
     * 
     * @param userId the ID of the user
     * @param rawPassword the password to check
     * @return true if the password matches, false otherwise or if the user doesn't exist
     */
    public boolean checkPasswordById(Long userId, String rawPassword) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }
}