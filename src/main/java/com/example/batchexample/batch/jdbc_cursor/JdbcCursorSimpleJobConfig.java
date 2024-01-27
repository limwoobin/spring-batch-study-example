package com.example.batchexample.batch.jdbc_cursor;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JdbcCursorSimpleJobConfig {
  private static final String JOB_NAME = "jdbcCursorSimpleJob";

  private final JobBuilderFactory jobBuilderFactory;
  private final Step jdbcCursorSimpleStep;

  public JdbcCursorSimpleJobConfig(JobBuilderFactory jobBuilderFactory,
                                   @Qualifier(value = JdbcCursorSimpleStepConfig.STEP_NAME) Step jdbcCursorSimpleStep) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.jdbcCursorSimpleStep = jdbcCursorSimpleStep;
  }

  @Bean
  public Job jdbcCursorSimpleJob() {
    return jobBuilderFactory.get(JOB_NAME)
      .start(jdbcCursorSimpleStep)
      .build();
  }
}
