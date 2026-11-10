package com.taskforge.job.dto;

import com.taskforge.job.domain.JobType;
import tools.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotNull;

public record CreateJobRequest(
        @NotNull(message = "Job type is required")
        JobType type,

        @NotNull(message = "Job payload is required")
        JsonNode payload
) {}
