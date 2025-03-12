package org.isp.repository;

import org.isp.model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    // Custom query to find accommodations by name
    List<Accommodation> findByName(String name);

    // Custom query to find accommodations by address containing a keyword
    List<Accommodation> findByAddressContaining(String keyword);
}