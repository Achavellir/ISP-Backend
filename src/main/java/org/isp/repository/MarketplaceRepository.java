package org.isp.repository;

import org.isp.model.MarketplaceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketplaceRepository extends JpaRepository<MarketplaceItem, Long> {
    // Custom query to find items by title
    List<MarketplaceItem> findByTitle(String title);

    // Custom query to find items by description containing a keyword
    List<MarketplaceItem> findByDescriptionContaining(String keyword);

    // Custom query to find items by price range
    List<MarketplaceItem> findByPriceBetween(double minPrice, double maxPrice);
}