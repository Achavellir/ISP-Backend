# Password Checking Functionality

This document explains how to check passwords against the database in the ISP Backend application.

## Overview

The application uses Spring Security with BCrypt password hashing for secure authentication. Passwords are never stored in plain text in the database; they are hashed using BCrypt before storage.

## How Password Verification Works

1. When a user registers, their password is hashed using BCryptPasswordEncoder and stored in the database.
2. During login, Spring Security's AuthenticationManager compares the provided password with the hashed password in the database.
3. For other password verification needs, the application provides a dedicated PasswordCheckService.

## Using PasswordCheckService

The `PasswordCheckService` provides methods to check if a provided password matches the password stored in the database for a user.

### Dependency Injection

```java
@Autowired
private PasswordCheckService passwordCheckService;
```

### Available Methods

1. **Check password by username**:
   ```java
   boolean matches = passwordCheckService.checkPasswordByUsername(username, rawPassword);
   ```

2. **Check password by email**:
   ```java
   boolean matches = passwordCheckService.checkPasswordByEmail(email, rawPassword);
   ```

3. **Check password by user ID**:
   ```java
   boolean matches = passwordCheckService.checkPasswordById(userId, rawPassword);
   ```

### Return Value

All methods return:
- `true` if the password matches the stored password
- `false` if the password doesn't match or the user doesn't exist

## REST API Endpoints

The application also provides REST API endpoints for password checking (restricted to ADMIN users):

1. **Check by username**:
   ```
   POST /api/password/check-by-username
   ```
   Request body:
   ```json
   {
     "identifier": "username",
     "password": "password123"
   }
   ```

2. **Check by email**:
   ```
   POST /api/password/check-by-email
   ```
   Request body:
   ```json
   {
     "identifier": "user@example.com",
     "password": "password123"
   }
   ```

3. **Check by user ID**:
   ```
   POST /api/password/check-by-id/{userId}?password=password123
   ```

### Response Format

All endpoints return a JSON object with a boolean "matches" field:

For a successful match:
```json
{
  "matches": true
}
```

For a failed match:
```json
{
  "matches": false
}
```

## Security Considerations

1. Password checking should be limited to authorized users (e.g., administrators).
2. Avoid exposing detailed error messages that could reveal user existence.
3. Consider implementing rate limiting to prevent brute force attacks.
4. Log password check attempts for security auditing.
