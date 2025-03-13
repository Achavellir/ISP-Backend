package org.isp.service;

import org.isp.model.Job;
import org.isp.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // Save a new job
    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    // Get all jobs
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // Get a job by ID
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    // Delete a job by ID
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }

    // Find jobs by title
    public List<Job> findJobsByTitle(String title) {
        return jobRepository.findByTitle(title);
    }

    // Find jobs by location containing a keyword
    public List<Job> findJobsByLocationContaining(String keyword) {
        return jobRepository.findByLocationContaining(keyword);
    }

    // Find jobs by company
    public List<Job> findJobsByCompany(String company) {
        return jobRepository.findByCompany(company);
    }
}