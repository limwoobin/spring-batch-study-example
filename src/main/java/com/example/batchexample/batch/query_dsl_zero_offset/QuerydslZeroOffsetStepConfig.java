package com.example.batchexample.batch.query_dsl_zero_offset;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuerydslZeroOffsetStepConfig {
  public static final String STEP_NAME = "zeroOffsetStep";

  private final StepBuilderFactory stepBuilderFactory;

  public QuerydslZeroOffsetStepConfig(StepBuilderFactory stepBuilderFactory) {
    this.stepBuilderFactory = stepBuilderFactory;
  }

  @Bean
  public Step zeroOffsetStep() {
    return stepBuilderFactory.get(STEP_NAME)
      .tasklet(null)
      .build();
  }

}
