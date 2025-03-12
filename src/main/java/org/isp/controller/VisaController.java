package org.isp.controller;

import org.isp.model.Visa;
import org.isp.service.VisaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/visas")
public class VisaController {

    private final VisaService visaService;

    public VisaController(VisaService visaService) {
        this.visaService = visaService;
    }

    // Get all visas
    @GetMapping
    public ResponseEntity<List<Visa>> getAllVisas() {
        List<Visa> visas = visaService.getAllVisas();
        return ResponseEntity.ok(visas);
    }

    // Get a visa by ID
    @GetMapping("/{id}")
    public ResponseEntity<Visa> getVisaById(@PathVariable Long id) {
        Visa visa = visaService.getVisaById(id);
        return ResponseEntity.ok(visa);
    }

    // Create a new visa
    @PostMapping
    public ResponseEntity<Visa> createVisa(@Valid @RequestBody Visa visa) {
        Visa savedVisa = visaService.saveVisa(visa);
        return new ResponseEntity<>(savedVisa, HttpStatus.CREATED);
    }

    // Delete a visa by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisa(@PathVariable Long id) {
        visaService.deleteVisa(id);
        return ResponseEntity.noContent().build();
    }

    // Search visas by type
    @GetMapping("/search/type")
    public ResponseEntity<List<Visa>> searchVisasByType(@RequestParam String type) {
        List<Visa> visas = visaService.findVisasByType(type);
        return ResponseEntity.ok(visas);
    }

    // Search visas by country
    @GetMapping("/search/country")
    public ResponseEntity<List<Visa>> searchVisasByCountry(@RequestParam String country) {
        List<Visa> visas = visaService.findVisasByCountry(country);
        return ResponseEntity.ok(visas);
    }

    // Search visas by requirements containing a keyword
    @GetMapping("/search/requirements")
    public ResponseEntity<List<Visa>> searchVisasByRequirements(@RequestParam String keyword) {
        List<Visa> visas = visaService.findVisasByRequirementsContaining(keyword);
        return ResponseEntity.ok(visas);
    }
}