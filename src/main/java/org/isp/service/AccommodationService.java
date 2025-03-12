package org.isp.service;

import org.isp.exception.ResourceNotFoundException;
import org.isp.model.Accommodation;
import org.isp.repository.AccommodationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;

    public AccommodationService(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    // Get all accommodations
    public List<Accommodation> getAllAccommodations() {
        return accommodationRepository.findAll();
    }

    // Get accommodation by ID
    public Accommodation getAccommodationById(Long id) {
        return accommodationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Accommodation not found with id: " + id));
    }

    // Create a new accommodation
    public Accommodation saveAccommodation(Accommodation accommodation) {
        return accommodationRepository.save(accommodation);
    }

    // Delete accommodation by ID
    public void deleteAccommodation(Long id) {
        accommodationRepository.deleteById(id);
    }

    // Custom query: Find accommodations by name
    public List<Accommodation> findAccommodationsByName(String name) {
        return accommodationRepository.findByName(name);
    }

    // Custom query: Find accommodations by address containing a keyword
    public List<Accommodation> findAccommodationsByAddressContaining(String keyword) {
        return accommodationRepository.findByAddressContaining(keyword);
    }
}