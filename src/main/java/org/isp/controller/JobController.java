package org.isp.controller;

import org.isp.model.Job;
import org.isp.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // Get all jobs
    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    // Get a job by ID
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        Job job = jobService.getJobById(id);
        return ResponseEntity.ok(job);
    }

    // Create a new job
    @PostMapping
    public ResponseEntity<Job> createJob(@Valid @RequestBody Job job) {
        Job savedJob = jobService.saveJob(job);
        return new ResponseEntity<>(savedJob, HttpStatus.CREATED);
    }

    // Delete a job by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

    // Search jobs by title
    @GetMapping("/search/title")
    public ResponseEntity<List<Job>> searchJobsByTitle(@RequestParam String title) {
        List<Job> jobs = jobService.findJobsByTitle(title);
        return ResponseEntity.ok(jobs);
    }

    // Search jobs by location containing a keyword
    @GetMapping("/search/location")
    public ResponseEntity<List<Job>> searchJobsByLocation(@RequestParam String keyword) {
        List<Job> jobs = jobService.findJobsByLocationContaining(keyword);
        return ResponseEntity.ok(jobs);
    }

    // Search jobs by company
    @GetMapping("/search/company")
    public ResponseEntity<List<Job>> searchJobsByCompany(@RequestParam String company) {
        List<Job> jobs = jobService.findJobsByCompany(company);
        return ResponseEntity.ok(jobs);
    }
}