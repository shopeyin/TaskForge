package com.taskforge.job.domain;

import tools.jackson.databind.JsonNode;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

public class Job {
    private final UUID id;
    private final JobType type;
    private JobStatus status;
    private JsonNode payload;
    private int attempts;
    private final Instant createdAt;
    private Instant startedAt;
    private Instant completedAt;
    private final Clock clock;


    public Job(JobType type, JsonNode payload) {
        this(type, payload, Clock.systemUTC());
    }

    public Job(JobType type) {
        this(type,null, Clock.systemUTC());
    }
    public Job(JobType type, JsonNode payload, Clock clock){
        if(type == null){
            throw new IllegalArgumentException("Job type must not be null");
        }
        this.id = UUID.randomUUID();
       this.type = type;
       this.payload = payload;
       this.status = JobStatus.QUEUED;
       this.clock = clock;
        this.createdAt = clock.instant();
    }

    public JobStatus getStatus() {
        return status;
    }

    public int getAttempts(){
        return attempts;
    }

    public JobType getType(){
        return type;
    }

    public UUID getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void startProcessing() {
        if (status != JobStatus.QUEUED) {
            throw new IllegalStateException(
                    "Only queued jobs can start processing"
            );
        }
        this.status = JobStatus.PROCESSING;
        this.startedAt = clock.instant();
    }
    public Instant getStartedAt() {
        return startedAt;
    }

    public void complete(){
        if(status != JobStatus.PROCESSING){
            throw new IllegalStateException(
                    "Only processing jobs can be completed"
            );
        }
        this.status = JobStatus.COMPLETED;
        this.completedAt = clock.instant();
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public JsonNode getPayload() {
        return payload;
    }
}
