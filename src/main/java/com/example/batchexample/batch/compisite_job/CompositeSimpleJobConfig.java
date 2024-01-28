package com.example.batchexample.batch.compisite_job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompositeSimpleJobConfig {
  private static final String JOB_NAME = "compositeSimpleJob";

  private final JobBuilderFactory jobBuilderFactory;
  private final Step compositeSimpleStep;

  public CompositeSimpleJobConfig(JobBuilderFactory jobBuilderFactory,
                                  @Qualifier(value = CompositeSimpleStepConfig.STEP_NAME) Step compositeSimpleStep) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.compositeSimpleStep = compositeSimpleStep;
  }

  @Bean
  public Job compositeSimpleJob() {
    return jobBuilderFactory.get(JOB_NAME)
      .start(compositeSimpleStep)
      .build();
  }
}
