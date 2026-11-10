package com.taskforge.job.entity;
import tools.jackson.databind.JsonNode;
import com.taskforge.job.domain.Job;
import com.taskforge.job.domain.JobStatus;
import com.taskforge.job.domain.JobType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "jobs")
public class JobEntity {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private JobType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private JobStatus status;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false, columnDefinition = "jsonb")
    private JsonNode payload;

    @Column(nullable = false)
    private int attempts;

    @Column(nullable = false)
    private Instant createdAt;

    private Instant startedAt;

    private Instant completedAt;

    protected JobEntity() {
        // Required by JPA
    }

    public static JobEntity fromDomain(Job job) {
        JobEntity entity = new JobEntity();

        entity.id = job.getId();
        entity.type = job.getType();
        entity.status = job.getStatus();
        entity.payload = job.getPayload();
        entity.attempts = job.getAttempts();
        entity.createdAt = job.getCreatedAt();
        entity.startedAt = job.getStartedAt();
        entity.completedAt = job.getCompletedAt();

        return entity;
    }

    public UUID getId() {
        return id;
    }

    public JobType getType() {
        return type;
    }

    public JobStatus getStatus() {
        return status;
    }

    public JsonNode getPayload() {
        return payload;
    }

    public int getAttempts() {
        return attempts;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }
}