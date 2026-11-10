package com.taskforge.job.service;

import com.taskforge.job.domain.Job;
import com.taskforge.job.dto.CreateJobRequest;
import com.taskforge.job.dto.JobResponse;
import com.taskforge.job.entity.JobEntity;
import com.taskforge.job.repository.JobRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }
    @Transactional
    public JobResponse createJob(CreateJobRequest request) {

        Job job = new Job(
                request.type(),
                request.payload()
        );

        JobEntity entity = JobEntity.fromDomain(job);

        JobEntity savedEntity = jobRepository.save(entity);

        return new JobResponse(
                savedEntity.getId(),
                savedEntity.getType(),
                savedEntity.getStatus(),
                savedEntity.getPayload(),
                savedEntity.getAttempts(),
                savedEntity.getCreatedAt(),
                savedEntity.getStartedAt(),
                savedEntity.getCompletedAt()
        );
    }
}