package com.taskforge.job.controller;

import com.taskforge.job.dto.JobResponse;
import com.taskforge.job.dto.CreateJobRequest;
import com.taskforge.job.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
@RequestMapping("/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }


    @PostMapping
    public ResponseEntity<JobResponse> createJob(@Valid @RequestBody CreateJobRequest request) {

        JobResponse response = jobService.createJob(request);

        URI location = URI.create("/jobs/" + response.id());

        return ResponseEntity
                .created(location)
                .body(response);
    }
}