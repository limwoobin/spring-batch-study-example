package com.example.batchexample.batch.scope;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScopeSimpleJobConfig {
  private static final String JOB_NAME = "scopeSimpleJob";

  private final JobBuilderFactory jobBuilderFactory;
  private final Step scopeSimpleStep;

  public ScopeSimpleJobConfig(JobBuilderFactory jobBuilderFactory,
                              @Qualifier(value = ScopeSimpleStepConfig.STEP_NAME) Step scopeSimpleStep) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.scopeSimpleStep = scopeSimpleStep;
  }

  @Bean
  public Job scopeSimpleJob() {
    return jobBuilderFactory.get(JOB_NAME)
      .start(scopeSimpleStep)
      .build();
  }
}
