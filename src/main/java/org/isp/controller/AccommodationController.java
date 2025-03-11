package com.example.controller;

import com.example.model.Accommodation;
import com.example.service.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accommodations")
public class AccommodationController {

    @Autowired
    private AccommodationService accommodationService;

    @GetMapping
    public List<Accommodation> getAllAccommodations() {
        return accommodationService.getAllAccommodations();
    }

    @PostMapping
    public Accommodation addAccommodation(@RequestBody Accommodation accommodation) {
        return accommodationService.addAccommodation(accommodation);
    }
}