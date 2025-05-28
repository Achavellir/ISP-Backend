package org.isp.controller;

import org.isp.model.Accommodation;
import org.isp.model.MarketplaceItem;
import org.isp.service.MarketplaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/marketplace")
public class MarketplaceController {

    private final MarketplaceService marketplaceService;

    public MarketplaceController(MarketplaceService marketplaceService) {
        this.marketplaceService = marketplaceService;
    }

    // Get all marketplace items
    @GetMapping
    public ResponseEntity<List<MarketplaceItem>> getAllItems() {
        List<MarketplaceItem> items = marketplaceService.getAllItems();
        return ResponseEntity.ok(items);
    }

    // Get a marketplace item by ID
    @GetMapping("/{id}")
    public ResponseEntity<MarketplaceItem> getItemById(@PathVariable Long id) {
        MarketplaceItem item = marketplaceService.getItemById(id);
        return ResponseEntity.ok(item);
    }

    // Create a new marketplace item
    @PostMapping
    public ResponseEntity<MarketplaceItem> createItem(@Valid @RequestBody MarketplaceItem item) {
        MarketplaceItem savedItem = marketplaceService.saveItem(item);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    // Delete a marketplace item by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        marketplaceService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    // Search items by title
    @GetMapping("/search/title")
    public ResponseEntity<List<MarketplaceItem>> searchItemsByTitle(@RequestParam String title) {
        List<MarketplaceItem> items = marketplaceService.findItemsByTitle(title);
        return ResponseEntity.ok(items);
    }

    // Search items by description containing a keyword
    @GetMapping("/search/description")
    public ResponseEntity<List<MarketplaceItem>> searchItemsByDescription(@RequestParam String keyword) {
        List<MarketplaceItem> items = marketplaceService.findItemsByDescriptionContaining(keyword);
        return ResponseEntity.ok(items);
    }

    // Search items by price range
    @GetMapping("/search/price")
    public ResponseEntity<List<MarketplaceItem>> searchItemsByPriceRange(
            @RequestParam double minPrice,
            @RequestParam double maxPrice) {
        List<MarketplaceItem> items = marketplaceService.findItemsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(items);
    }
}
