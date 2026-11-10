package com.taskforge.job.dto;

import com.taskforge.job.domain.JobStatus;
import com.taskforge.job.domain.JobType;
import tools.jackson.databind.JsonNode;
import java.time.Instant;
import java.util.UUID;


public record JobResponse(
        UUID id,
        JobType type,
        JobStatus status,
        JsonNode payload,
        int attempts,
        Instant createdAt,
        Instant startedAt,
        Instant completedAt
) {}