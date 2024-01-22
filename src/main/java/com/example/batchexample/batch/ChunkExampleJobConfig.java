package com.example.batchexample.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChunkExampleJobConfig {
  private static final String JOB_NAME = "chunkExampleJob1";

  private final JobBuilderFactory jobBuilderFactory;
  private final Step chunkExampleStep;

  public ChunkExampleJobConfig(JobBuilderFactory jobBuilderFactory, Step chunkExampleStep) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.chunkExampleStep = chunkExampleStep;
  }

  @Bean
  public Job chunkExampleJob() {
    return jobBuilderFactory.get(JOB_NAME)
      .incrementer(new CustomRunIdIncrementer())
      .start(chunkExampleStep)
      .build();
  }

}
