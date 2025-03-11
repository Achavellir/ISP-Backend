package com.example.service;

import com.example.model.Accommodation;
import com.example.repository.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationService {

    @Autowired
    private AccommodationRepository accommodationRepository;

    public List<Accommodation> getAllAccommodations() {
        return accommodationRepository.findAll();
    }

    public Accommodation addAccommodation(Accommodation accommodation) {
        return accommodationRepository.save(accommodation);
    }
}