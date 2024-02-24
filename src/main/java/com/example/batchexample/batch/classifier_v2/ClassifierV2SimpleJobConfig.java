package com.example.batchexample.batch.classifier_v2;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClassifierV2SimpleJobConfig {
  private static final String JOB_NAME = "classifierV2SimpleJob";

  private final JobBuilderFactory jobBuilderFactory;
  private final Step classifierV2SimpleStep;

  public ClassifierV2SimpleJobConfig(JobBuilderFactory jobBuilderFactory,
                                     @Qualifier(value = ClassifierV2SimpleStepConfig.STEP_NAME) Step classifierV2SimpleStep) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.classifierV2SimpleStep = classifierV2SimpleStep;
  }

  @Bean
  public Job classifierV2SimpleJob() {
    return jobBuilderFactory.get(JOB_NAME)
      .start(classifierV2SimpleStep)
      .build();
  }
}
