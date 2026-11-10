package com.taskforge.job.repository;

import com.taskforge.job.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobRepository
        extends JpaRepository<JobEntity, UUID> {
}