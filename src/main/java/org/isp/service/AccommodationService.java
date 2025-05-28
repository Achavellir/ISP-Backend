package org.isp.service;

import org.isp.event.AccommodationEvent;
import org.isp.exception.ResourceNotFoundException;
import org.isp.model.Accommodation;
import org.isp.repository.AccommodationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final ApplicationEventPublisher eventPublisher;
    private static final Logger logger = LoggerFactory.getLogger(AccommodationService.class);

    public AccommodationService(AccommodationRepository accommodationRepository,
                              ApplicationEventPublisher eventPublisher) {
        this.accommodationRepository = accommodationRepository;
        this.eventPublisher = eventPublisher;
    }

    @Cacheable(value = "accommodations", key = "'all'")
    public List<Accommodation> getAllAccommodations() {
        logger.info("Fetching all accommodations");
        return accommodationRepository.findAll();
    }

    @Async("taskExecutor")
    public CompletableFuture<List<Accommodation>> getAllAccommodationsAsync() {
        logger.info("Fetching all accommodations asynchronously");
        return CompletableFuture.completedFuture(getAllAccommodations());
    }

    @Cacheable(value = "accommodations", key = "#id")
    public Accommodation getAccommodationById(Long id) {
        logger.info("Fetching accommodation with id: {}", id);
        return accommodationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Accommodation not found with id: " + id));
    }

    @Async("taskExecutor")
    public CompletableFuture<Accommodation> getAccommodationByIdAsync(Long id) {
        logger.info("Fetching accommodation by id asynchronously: {}", id);
        return CompletableFuture.completedFuture(getAccommodationById(id));
    }

    @Transactional
    @CachePut(value = "accommodations", key = "#result.id")
    public Accommodation saveAccommodation(Accommodation accommodation) {
        logger.info("Saving accommodation: {}", accommodation);
        try {
            Accommodation saved = accommodationRepository.save(accommodation);
            eventPublisher.publishEvent(new AccommodationEvent(this, "CREATED", saved));
            return saved;
        } catch (Exception ex) {
            logger.error("Error saving accommodation: {}", accommodation, ex);
            throw ex; // Re-throw the exception to let the transaction rollback
        }
    }

    @Transactional
    @CachePut(value = "accommodations", key = "#result.id")
    public Accommodation updateAccommodation(Long id, Accommodation accommodation) {
        logger.info("Updating accommodation with id: {}", id);
        Accommodation existing = getAccommodationById(id);
        // Update all fields
        existing.setName(accommodation.getName());
        existing.setAddress(accommodation.getAddress());
        existing.setDescription(accommodation.getDescription());
        existing.setPrice(accommodation.getPrice());

        Accommodation updated = accommodationRepository.save(existing);
        eventPublisher.publishEvent(new AccommodationEvent(this, "UPDATED", updated));
        return updated;
    }

    @Transactional
    @CacheEvict(value = "accommodations", key = "#id")
    public void deleteAccommodation(Long id) {
        logger.info("Deleting accommodation with id: {}", id);
        Accommodation accommodation = getAccommodationById(id);
        accommodationRepository.deleteById(id);
        eventPublisher.publishEvent(new AccommodationEvent(this, "DELETED", accommodation));
    }

    @Cacheable(value = "accommodations", key = "'name:' + #name")
    public List<Accommodation> findAccommodationsByName(String name) {
        logger.info("Finding accommodations by name: {}", name);
        return accommodationRepository.findByName(name);
    }

    @Async("taskExecutor")
    public CompletableFuture<List<Accommodation>> findAccommodationsByNameAsync(String name) {
        logger.info("Finding accommodations by name asynchronously: {}", name);
        return CompletableFuture.completedFuture(findAccommodationsByName(name));
    }

    @Cacheable(value = "accommodations", key = "'address:' + #keyword")
    public List<Accommodation> findAccommodationsByAddressContaining(String keyword) {
        logger.info("Finding accommodations by address containing: {}", keyword);
        return accommodationRepository.findByAddressContaining(keyword);
    }

    @Async("taskExecutor")
    public CompletableFuture<List<Accommodation>> findAccommodationsByAddressContainingAsync(String keyword) {
        logger.info("Finding accommodations by address containing asynchronously: {}", keyword);
        return CompletableFuture.completedFuture(findAccommodationsByAddressContaining(keyword));
    }

    @CacheEvict(value = "accommodations", allEntries = true)
    public void clearCache() {
        logger.info("Clearing accommodations cache");
        // This method will clear all entries in the accommodations cache
    }
}
