CREATE TABLE jobs (
                      id UUID PRIMARY KEY,

                      type VARCHAR(50) NOT NULL,
                      status VARCHAR(50) NOT NULL,

                      payload JSONB NOT NULL,

                      attempts INTEGER NOT NULL DEFAULT 0,

                      created_at TIMESTAMPTZ NOT NULL,
                      started_at TIMESTAMPTZ,
                      completed_at TIMESTAMPTZ,

                      CONSTRAINT chk_jobs_attempts
                          CHECK (attempts >= 0),

                      CONSTRAINT chk_jobs_type
                          CHECK (type IN (
                                          'SEND_EMAIL',
                                          'GENERATE_REPORT',
                                          'PROCESS_FILE'
                              )),

                      CONSTRAINT chk_jobs_status
                          CHECK (status IN (
                                            'QUEUED',
                                            'PROCESSING',
                                            'COMPLETED',
                                            'FAILED'
                              ))
);