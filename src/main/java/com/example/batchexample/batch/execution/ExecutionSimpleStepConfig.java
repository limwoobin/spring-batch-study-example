package com.example.batchexample.batch.execution;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutionSimpleStepConfig {
  public static final String STEP_NAME = "executionSimpleStep";
  public static final String STEP_NAME2 = "executionSimpleStep2";
  public static final String STEP_NAME3 = "executionSimpleStep3";

  private final StepBuilderFactory stepBuilderFactory;
  private final ExecutionSimpleItemReader executionSimpleItemReader;
  private final ExecutionSimpleItemWriter executionSimpleItemWriter;

  private final ExecutionTasklet executionTasklet;
  private final ExecutionTasklet2 executionTasklet2;

  public ExecutionSimpleStepConfig(StepBuilderFactory stepBuilderFactory,
                                   ExecutionSimpleItemReader executionSimpleItemReader,
                                   ExecutionSimpleItemWriter executionSimpleItemWriter,
                                   ExecutionTasklet executionTasklet,
                                   ExecutionTasklet2 executionTasklet2) {
    this.stepBuilderFactory = stepBuilderFactory;
    this.executionSimpleItemReader = executionSimpleItemReader;
    this.executionSimpleItemWriter = executionSimpleItemWriter;
    this.executionTasklet = executionTasklet;
    this.executionTasklet2 = executionTasklet2;
  }

  @Bean
//  @JobScope
  public Step executionSimpleStep() {
    return stepBuilderFactory.get(STEP_NAME)
      .listener(executionTasklet)
      .tasklet(executionTasklet)
      .build();
  }

  @Bean
//  @JobScope
  public Step executionSimpleStep2() {
    return stepBuilderFactory.get(STEP_NAME2)
      .tasklet(executionTasklet2)
      .build();
  }

  @Bean
  public Step executionSimpleStep3() {
    return stepBuilderFactory.get(STEP_NAME3)
      .<Long, Long>chunk(5)
      .reader(executionSimpleItemReader)
      .writer(executionSimpleItemWriter)
      .build();
  }
}
