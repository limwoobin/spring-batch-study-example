package com.example.batchexample.batch.kafka;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaSimpleJobConfig {
  private static final String JOB_NAME = "kafkaSimpleJob";

  private final JobBuilderFactory jobBuilderFactory;
  private final Step kafkaSimpleStep;

  public KafkaSimpleJobConfig(JobBuilderFactory jobBuilderFactory,
                              @Qualifier(value = KafkaSimpleStepConfig.STEP_NAME) Step kafkaSimpleStep) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.kafkaSimpleStep = kafkaSimpleStep;
  }

  @Bean
  public Job kafkaSimpleJob() {
    return jobBuilderFactory.get(JOB_NAME)
      .start(kafkaSimpleStep)
      .build();
  }

}
