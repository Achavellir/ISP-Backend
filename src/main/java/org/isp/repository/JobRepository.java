package org.isp.repository;

import org.isp.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    // Custom query to find jobs by title
    List<Job> findByTitle(String title);

    // Custom query to find jobs by location containing a keyword
    List<Job> findByLocationContaining(String keyword);

    // Custom query to find jobs by company
    List<Job> findByCompany(String company);
}