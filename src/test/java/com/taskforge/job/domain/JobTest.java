package com.taskforge.job.domain;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import static org.junit.jupiter.api.Assertions.*;

class JobTest {

    @Test
    void newJobShouldStartQueued()
    {

        Job job = new Job(JobType.GENERATE_REPORT);

        assertEquals(JobStatus.QUEUED, job.getStatus());
    }

    @Test
    void newJobShouldStartWithZeroAttempt()
    {
        Job job = new Job(JobType.GENERATE_REPORT);

        assertEquals(0, job.getAttempts());
    }


    @Test
    void newJobShouldRequireType() {

        assertThrows(IllegalArgumentException.class,
                () -> new Job(null)
        );
    }

    @Test
    void newJobShouldRetainItsType(){
        Job job = new Job(JobType.SEND_EMAIL);
        assertEquals(JobType.SEND_EMAIL, job.getType());
    }

    @Test
    void newJobShouldHaveAnId() {

        Job job = new Job(JobType.GENERATE_REPORT);

        assertNotNull(job.getId());
    }

    @Test
    void newJobsShouldHaveDifferentIds() {

        Job firstJob = new Job(JobType.GENERATE_REPORT);
        Job secondJob = new Job(JobType.GENERATE_REPORT);

        assertNotEquals(firstJob.getId(), secondJob.getId());
    }

    @Test
    void newJobShouldRecordCreationTime() {

        Instant fixedTime =
                Instant.parse("2026-09-26T14:00:00Z");

        Clock clock =
                Clock.fixed(fixedTime, ZoneOffset.UTC);

        Job job =
                new Job(JobType.GENERATE_REPORT,null, clock);

        assertEquals(fixedTime, job.getCreatedAt());
    }

    @Test
    void queuedJobCanStartProcessing() {

        Job job = new Job(JobType.GENERATE_REPORT);

        job.startProcessing();

        assertEquals(JobStatus.PROCESSING, job.getStatus());
    }

    @Test
    void startingJobShouldRecordStartedAt() {

        Instant fixedTime =
                Instant.parse("2026-09-26T14:00:00Z");

        Clock clock =
                Clock.fixed(fixedTime, ZoneOffset.UTC);

        Job job =
                new Job(JobType.GENERATE_REPORT, null, clock);

        job.startProcessing();

        assertEquals(fixedTime, job.getStartedAt());
    }

    @Test
    void processingJobCannotStartProcessingAgain() {

        Job job = new Job(JobType.GENERATE_REPORT);

        job.startProcessing();

        assertThrows(
                IllegalStateException.class,
                job::startProcessing
        );
    }

    @Test
    void processingJobCanComplete() {

        Job job = new Job(JobType.GENERATE_REPORT);

        job.startProcessing();
        job.complete();

        assertEquals(JobStatus.COMPLETED, job.getStatus());
    }

    @Test
    void completingJobShouldRecordCompletedAt() {

        Instant fixedTime =
                Instant.parse("2026-09-26T14:00:00Z");

        Clock clock =
                Clock.fixed(fixedTime, ZoneOffset.UTC);

        Job job =
                new Job(JobType.GENERATE_REPORT,null, clock);

        job.startProcessing();
        job.complete();

        assertEquals(fixedTime, job.getCompletedAt());
    }

    @Test
    void newJobShouldRetainPayload() {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode payload = objectMapper.createObjectNode()
                .put("reportId", "12345");

        Job job = new Job(
                JobType.GENERATE_REPORT,
                payload
        );

        assertEquals(payload, job.getPayload());
    }
}