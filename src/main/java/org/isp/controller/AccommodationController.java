package org.isp.controller;

import org.isp.exception.ResourceNotFoundException;
import org.isp.model.Accommodation;
import org.isp.service.AccommodationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/accommodations")
public class AccommodationController {

    private final AccommodationService accommodationService;

    public AccommodationController(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @GetMapping
    public ResponseEntity<List<Accommodation>> getAllAccommodations() {
        List<Accommodation> accommodations = accommodationService.getAllAccommodations();
        return ResponseEntity.ok(accommodations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Accommodation> getAccommodationById(@PathVariable Long id) {
        Accommodation accommodation = accommodationService.getAccommodationById(id);
        return ResponseEntity.ok(accommodation);
    }

    @PostMapping
    public ResponseEntity<Accommodation> createAccommodation(@Valid @RequestBody Accommodation accommodation) {
        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation);
        return new ResponseEntity<>(savedAccommodation, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable Long id) {
        accommodationService.deleteAccommodation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Accommodation>> searchAccommodations(@RequestParam String keyword) {
        List<Accommodation> accommodations = accommodationService.findAccommodationsByAddressContaining(keyword);
        return ResponseEntity.ok(accommodations);
    }
}