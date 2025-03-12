package org.isp.service;

import org.isp.exception.ResourceNotFoundException;
import org.isp.model.Visa;
import org.isp.repository.VisaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisaService {

    private final VisaRepository visaRepository;

    public VisaService(VisaRepository visaRepository) {
        this.visaRepository = visaRepository;
    }

    // Get all visas
    public List<Visa> getAllVisas() {
        return visaRepository.findAll();
    }

    // Get a visa by ID
    public Visa getVisaById(Long id) {
        return visaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visa not found with id: " + id));
    }

    // Save a new visa
    public Visa saveVisa(Visa visa) {
        return visaRepository.save(visa);
    }

    // Delete a visa by ID
    public void deleteVisa(Long id) {
        visaRepository.deleteById(id);
    }

    // Custom query: Find visas by type
    public List<Visa> findVisasByType(String type) {
        return visaRepository.findByType(type);
    }

    // Custom query: Find visas by country
    public List<Visa> findVisasByCountry(String country) {
        return visaRepository.findByCountry(country);
    }

    // Custom query: Find visas by requirements containing a keyword
    public List<Visa> findVisasByRequirementsContaining(String keyword) {
        return visaRepository.findByRequirementsContaining(keyword);
    }
}