package org.isp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.isp.exception.ResourceNotFoundException;
import org.isp.model.Accommodation;
import org.isp.service.AccommodationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping({"/api/v1/accommodations"})
@Tag(name = "Accommodation", description = "Accommodation management APIs")
@SecurityRequirement(name = "bearerAuth")
public class AccommodationController {

    private final AccommodationService accommodationService;
    private static final Logger logger = LoggerFactory.getLogger(AccommodationController.class);

    public AccommodationController(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @GetMapping
    @Operation(summary = "Get all accommodations")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public CompletableFuture<ResponseEntity<List<Accommodation>>> getAllAccommodations() {
        logger.info("Fetching all accommodations");
        return accommodationService.getAllAccommodationsAsync()
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    logger.error("Error fetching all accommodations", ex);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get accommodation by ID")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public CompletableFuture<ResponseEntity<Accommodation>> getAccommodationById(@PathVariable Long id) {
        logger.info("Fetching accommodation with id: {}", id);
        return accommodationService.getAccommodationByIdAsync(id)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    logger.error("Error fetching accommodation with id: {}", id, ex);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    @PostMapping
    @Operation(summary = "Create a new accommodation")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Accommodation> createAccommodation(@Valid @RequestBody Accommodation accommodation) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(accommodation);
            logger.info("Creating accommodation: {}", json);
            return new ResponseEntity<>(accommodationService.saveAccommodation(accommodation), HttpStatus.CREATED);
        } catch (Exception ex) {
            logger.error("Error creating accommodation", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing accommodation")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Accommodation> updateAccommodation(
            @PathVariable Long id,
            @Valid @RequestBody Accommodation accommodation) {
        logger.info("Updating accommodation with id: {}", id);
        try {
            return ResponseEntity.ok(accommodationService.updateAccommodation(id, accommodation));
        } catch (Exception ex) {
            logger.error("Error updating accommodation with id: {}", id, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an accommodation")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable Long id) {
        logger.info("Deleting accommodation with id: {}", id);
        try {
            accommodationService.deleteAccommodation(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            logger.error("Error deleting accommodation with id: {}", id, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search/name/{name}")
    @Operation(summary = "Search accommodations by name")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public CompletableFuture<ResponseEntity<List<Accommodation>>> findAccommodationsByName(@PathVariable String name) {
        logger.info("Searching accommodations by name: {}", name);
        return accommodationService.findAccommodationsByNameAsync(name)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    logger.error("Error searching accommodations by name: {}", name, ex);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    @GetMapping("/search/address/{keyword}")
    @Operation(summary = "Search accommodations by address")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public CompletableFuture<ResponseEntity<List<Accommodation>>> findAccommodationsByAddress(@PathVariable String keyword) {
        logger.info("Searching accommodations by address: {}", keyword);
        return accommodationService.findAccommodationsByAddressContainingAsync(keyword)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex -> {
                    logger.error("Error searching accommodations by address: {}", keyword, ex);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }
}