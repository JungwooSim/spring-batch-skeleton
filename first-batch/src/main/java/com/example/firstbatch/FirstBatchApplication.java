package com.example.firstbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class FirstBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstBatchApplication.class, args);
    }

}
