package org.isp.repository;

import org.isp.model.Visa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisaRepository extends JpaRepository<Visa, Long> {
    // Custom query to find visas by type
    List<Visa> findByType(String type);

    // Custom query to find visas by country
    List<Visa> findByCountry(String country);

    // Custom query to find visas by requirements containing a keyword
    List<Visa> findByRequirementsContaining(String keyword);
}