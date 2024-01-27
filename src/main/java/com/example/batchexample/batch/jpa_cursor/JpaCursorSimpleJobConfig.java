package com.example.batchexample.batch.jpa_cursor;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaCursorSimpleJobConfig {
  private static final String JOB_NAME = "jpaCursorSimpleJob";

  private final JobBuilderFactory jobBuilderFactory;
  private final Step jpaCursorSimpleStep;

  public JpaCursorSimpleJobConfig(JobBuilderFactory jobBuilderFactory,
                                  @Qualifier(value = JpaCursorSimpleStepConfig.STEP_NAME) Step jpaCursorSimpleStep) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.jpaCursorSimpleStep = jpaCursorSimpleStep;
  }

  @Bean
  public Job jpaCursorSimpleJob() {
    return jobBuilderFactory.get(JOB_NAME)
      .start(jpaCursorSimpleStep)
      .build();
  }
}
