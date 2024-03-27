package com.example.batchexample.batch.execution;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutionSimpleJobConfig {
  private static final String JOB_NAME = "executionSimpleJob";
  private static final String JOB_NAME2 = "executionSimpleJob2";

  private final JobBuilderFactory jobBuilderFactory;
  private final Step executionSimpleStep;
  private final Step executionSimpleStep2;
  private final Step executionSimpleStep3;

  public ExecutionSimpleJobConfig(JobBuilderFactory jobBuilderFactory,
                                  @Qualifier(value = ExecutionSimpleStepConfig.STEP_NAME) Step executionSimpleStep,
                                  @Qualifier(value = ExecutionSimpleStepConfig.STEP_NAME2) Step executionSimpleStep2,
                                  @Qualifier(value = ExecutionSimpleStepConfig.STEP_NAME3) Step executionSimpleStep3) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.executionSimpleStep = executionSimpleStep;
    this.executionSimpleStep2 = executionSimpleStep2;
    this.executionSimpleStep3 = executionSimpleStep3;
  }

  @Bean
  public Job executionSimpleJob() {
    return jobBuilderFactory.get(JOB_NAME)
      .start(executionSimpleStep)
      .next(executionSimpleStep2)
      .build();
  }

  @Bean
  public Job executionSimpleJob2() {
    return jobBuilderFactory.get(JOB_NAME2)
      .start(executionSimpleStep3)
      .build();
  }
}
