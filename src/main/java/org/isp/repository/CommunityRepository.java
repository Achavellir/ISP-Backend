package org.isp.repository;

import org.isp.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    // Custom query to find communities by name
    List<Community> findByName(String name);

    // Custom query to find communities by location containing a keyword
    List<Community> findByLocationContaining(String keyword);
}