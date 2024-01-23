package com.example.batchexample.batch.chunk_batch;

import com.example.batchexample.batch.CustomRunIdIncrementer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChunkBatchJobConfig {
  private static final String JOB_NAME = "chunkBatchJob";

  private final JobBuilderFactory jobBuilderFactory;
  private final Step chunkBatchStep;

  public ChunkBatchJobConfig(JobBuilderFactory jobBuilderFactory,
                             @Qualifier(value = ChunkBatchStepConfig.STEP_NAME) Step chunkBatchStep) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.chunkBatchStep = chunkBatchStep;
  }

  @Bean
  public Job chunkBatchJob() {
    return jobBuilderFactory.get(JOB_NAME)
      .incrementer(new CustomRunIdIncrementer())
      .start(chunkBatchStep)
      .build();
  }
}
