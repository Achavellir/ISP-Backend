package org.isp.service;

import org.isp.exception.ResourceNotFoundException;
import org.isp.model.MarketplaceItem;
import org.isp.repository.MarketplaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketplaceService {

    private final MarketplaceRepository marketplaceRepository;

    public MarketplaceService(MarketplaceRepository marketplaceRepository) {
        this.marketplaceRepository = marketplaceRepository;
    }

    // Get all marketplace items
    public List<MarketplaceItem> getAllItems() {
        return marketplaceRepository.findAll();
    }

    // Get a marketplace item by ID
    public MarketplaceItem getItemById(Long id) {
        return marketplaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marketplace item not found with id: " + id));
    }

    // Save a new marketplace item
    public MarketplaceItem saveItem(MarketplaceItem item) {
        return marketplaceRepository.save(item);
    }

    // Delete a marketplace item by ID
    public void deleteItem(Long id) {
        marketplaceRepository.deleteById(id);
    }

    // Custom query: Find items by title
    public List<MarketplaceItem> findItemsByTitle(String title) {
        return marketplaceRepository.findByTitle(title);
    }

    // Custom query: Find items by description containing a keyword
    public List<MarketplaceItem> findItemsByDescriptionContaining(String keyword) {
        return marketplaceRepository.findByDescriptionContaining(keyword);
    }

    // Custom query: Find items by price range
    public List<MarketplaceItem> findItemsByPriceRange(double minPrice, double maxPrice) {
        return marketplaceRepository.findByPriceBetween(minPrice, maxPrice);
    }
}