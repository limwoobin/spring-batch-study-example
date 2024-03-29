package com.example.batchexample.batch.query_dsl_zero_offset;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuerydslZeroOffsetJobConfig {
  private static final String JOB_NAME = "zeroOffsetJob";

  private final JobBuilderFactory jobBuilderFactory;
  private final Step zeroOffsetStep;

  public QuerydslZeroOffsetJobConfig(JobBuilderFactory jobBuilderFactory,
                                     Step zeroOffsetStep) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.zeroOffsetStep = zeroOffsetStep;
  }

  @Bean
  public Job zeroOffsetJob() {
    return jobBuilderFactory.get(JOB_NAME)
      .start(zeroOffsetStep)
      .build();
  }
}
