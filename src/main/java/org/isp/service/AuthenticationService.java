package org.isp.service;

import org.isp.dto.JwtResponse;
import org.isp.dto.LoginRequest;
import org.isp.dto.SignupRequest;
import org.isp.model.User;
import org.isp.repository.UserRepository;
import org.isp.security.JwtUtils;
import org.isp.security.TokenStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private TokenStorageService tokenStorageService;

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new JwtResponse(
                jwt,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles());
    }

    public User registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }

        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<String> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            roles.add("USER");
        } else {
            strRoles.forEach(role -> {
                switch (role.toLowerCase()) {
                    case "admin":
                        roles.add("ADMIN");
                        break;
                    case "moderator":
                        roles.add("MODERATOR");
                        break;
                    default:
                        roles.add("USER");
                }
            });
        }

        user.setRoles(roles);
        return userRepository.save(user);
    }

    /**
     * Logs out a user by invalidating their token.
     * This method blacklists the token in Redis, making it invalid for future requests.
     *
     * @param token the JWT token to invalidate
     */
    public void logout(String token) {
        if (token != null && !token.isEmpty()) {
            try {
                // Extract username from token for logging purposes
                String username = jwtUtils.getUserNameFromJwtToken(token);

                // Blacklist the token
                jwtUtils.invalidateToken(token);

                logger.info("User {} logged out successfully", username);
            } catch (Exception e) {
                logger.error("Error during logout: {}", e.getMessage());
            }
        }
    }

    /**
     * Invalidates all tokens for a specific user.
     * This is useful for scenarios like password change or account compromise.
     * Note: This method is currently not used in the application but is kept for future use
     * in security-related features such as "force logout from all devices" or when a user
     * changes their password.
     *
     * @param username the username whose tokens should be invalidated
     */
    @SuppressWarnings("unused")
    public void invalidateAllUserTokens(String username) {
        try {
            tokenStorageService.invalidateAllUserTokens(username);
            logger.info("Invalidated all tokens for user: {}", username);
        } catch (Exception e) {
            logger.error("Error invalidating tokens for user {}: {}", username, e.getMessage());
        }
    }
}
