package org.isp.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for password check requests
 */
public class PasswordCheckRequest {

    @NotBlank
    private String identifier; // username or email

    @NotBlank
    private String password;

    // Constructors
    public PasswordCheckRequest() {
    }

    public PasswordCheckRequest(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }

    // Getters and Setters
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}