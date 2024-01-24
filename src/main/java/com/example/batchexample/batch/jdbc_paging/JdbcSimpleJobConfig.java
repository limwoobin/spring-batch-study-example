package com.example.batchexample.batch.jdbc_paging;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JdbcSimpleJobConfig {
  public static final String JOB_NAME = "jdbcSimpleJob";

  private final JobBuilderFactory jobBuilderFactory;
  private final Step jdbcSimpleStep;

  public JdbcSimpleJobConfig(JobBuilderFactory jobBuilderFactory,
                             @Qualifier(value = JdbcSimpleStepConfig.STEP_NAME) Step jdbcSimpleStep) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.jdbcSimpleStep = jdbcSimpleStep;
  }

  @Bean
  public Job jdbcSimpleJob() {
    return jobBuilderFactory.get(JOB_NAME)
      .start(jdbcSimpleStep)
      .build();
  }

}
