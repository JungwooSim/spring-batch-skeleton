package com.example.firstbatch.batch.job;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBatchTest
@SpringBootTest(classes = {StockAggregate.class})
class StockAggregateTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void StockAggregateReader() throws Exception {
        //given

        //when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

    }

}
