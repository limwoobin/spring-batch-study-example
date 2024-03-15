package com.example.batchexample.batch.custom_kafka;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomKafkaSimpleJobConfig {
  private static final String JOB_NAME = "customKafkaSimpleJob";

  private final JobBuilderFactory jobBuilderFactory;
  private final Step customKafkaSimpleStep;

  public CustomKafkaSimpleJobConfig(JobBuilderFactory jobBuilderFactory,
                                    @Qualifier(value = CustomKafkaSimpleStepConfig.STEP_NAME) Step customKafkaSimpleStep) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.customKafkaSimpleStep = customKafkaSimpleStep;
  }

  @Bean
  public Job customKafkaSimpleJob() {
    return jobBuilderFactory.get(JOB_NAME)
      .start(customKafkaSimpleStep)
      .build();
  }
}
