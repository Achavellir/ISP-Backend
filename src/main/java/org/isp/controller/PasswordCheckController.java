package org.isp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.isp.dto.PasswordCheckRequest;
import org.isp.service.PasswordCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/password")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Password Check", description = "Password verification APIs")
public class PasswordCheckController {

    @Autowired
    private PasswordCheckService passwordCheckService;

    @Operation(summary = "Check password by username", description = "Check if the provided password matches the password stored for a user with the given username")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Password check completed"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/check-by-username")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> checkPasswordByUsername(@Valid @RequestBody PasswordCheckRequest request) {
        boolean matches = passwordCheckService.checkPasswordByUsername(request.getIdentifier(), request.getPassword());
        Map<String, Boolean> response = new HashMap<>();
        response.put("matches", matches);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Check password by email", description = "Check if the provided password matches the password stored for a user with the given email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Password check completed"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/check-by-email")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> checkPasswordByEmail(@Valid @RequestBody PasswordCheckRequest request) {
        boolean matches = passwordCheckService.checkPasswordByEmail(request.getIdentifier(), request.getPassword());
        Map<String, Boolean> response = new HashMap<>();
        response.put("matches", matches);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Check password by user ID", description = "Check if the provided password matches the password stored for a user with the given ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Password check completed"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/check-by-id/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> checkPasswordById(
            @PathVariable Long userId,
            @RequestParam String password) {
        boolean matches = passwordCheckService.checkPasswordById(userId, password);
        Map<String, Boolean> response = new HashMap<>();
        response.put("matches", matches);
        return ResponseEntity.ok(response);
    }
}