package com.example.batchexample.batch.classifier;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClassifierSimpleJobConfig {
  private static final String JOB_NAME = "classifierSimpleJob";

  private final JobBuilderFactory jobBuilderFactory;
  private final Step classifierSimpleStep;

  public ClassifierSimpleJobConfig(JobBuilderFactory jobBuilderFactory,
                                   @Qualifier(value = ClassifierSimpleStepConfig.STEP_NAME) Step classifierSimpleStep) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.classifierSimpleStep = classifierSimpleStep;
  }

  @Bean
  public Job classifierSimpleJob() {
    return jobBuilderFactory.get(JOB_NAME)
      .start(classifierSimpleStep)
      .build();
  }
}
