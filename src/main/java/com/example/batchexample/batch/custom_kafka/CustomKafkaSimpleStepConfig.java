package com.example.batchexample.batch.custom_kafka;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomKafkaSimpleStepConfig {
  public static final String STEP_NAME = "customKafkaSimpleStep";

  private final StepBuilderFactory stepBuilderFactory;
  private final CustomKafkaSimpleItemReader customKafkaSimpleItemReader;
  private final CustomKafkaSimpleItemWriter customKafkaSimpleItemWriter;

  public CustomKafkaSimpleStepConfig(StepBuilderFactory stepBuilderFactory,
                                     CustomKafkaSimpleItemReader customKafkaSimpleItemReader,
                                     CustomKafkaSimpleItemWriter customKafkaSimpleItemWriter) {
    this.stepBuilderFactory = stepBuilderFactory;
    this.customKafkaSimpleItemReader = customKafkaSimpleItemReader;
    this.customKafkaSimpleItemWriter = customKafkaSimpleItemWriter;
  }

  @Bean
  @JobScope
  public Step customKafkaSimpleStep() {
    return stepBuilderFactory.get(STEP_NAME)
      .<String, String>chunk(3)
      .reader(customKafkaSimpleItemReader.customKafkaItemReader())
      .writer(customKafkaSimpleItemWriter)
      .build();
  }
}
