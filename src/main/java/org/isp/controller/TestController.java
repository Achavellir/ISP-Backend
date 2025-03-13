package org.isp.controller;

import org.isp.model.Job;
import org.isp.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private JobService jobService;

    @GetMapping("/test")
    public String createTestJob() {
        Job job = new Job();
        job.setTitle("Test Job");
        job.setDescription("This is a test job description.");
        job.setCompany("Test Company");
        job.setLocation("Test Location");
        job.setSalary(50000);

        jobService.saveJob(job);

        return "Test job created successfully!";
    }
}