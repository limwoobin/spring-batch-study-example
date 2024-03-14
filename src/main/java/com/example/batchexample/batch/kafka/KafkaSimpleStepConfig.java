package com.example.batchexample.batch.kafka;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaSimpleStepConfig {
  public static final String STEP_NAME = "kafkaSimpleStep";

  private final StepBuilderFactory stepBuilderFactory;
  private final KafkaSimpleItemReader kafkaSimpleItemReader;
  private final KafkaSimpleItemWriter kafkaSimpleItemWriter;

  public KafkaSimpleStepConfig(StepBuilderFactory stepBuilderFactory,
                               KafkaSimpleItemReader kafkaSimpleItemReader,
                               KafkaSimpleItemWriter kafkaSimpleItemWriter) {
    this.stepBuilderFactory = stepBuilderFactory;
    this.kafkaSimpleItemReader = kafkaSimpleItemReader;
    this.kafkaSimpleItemWriter = kafkaSimpleItemWriter;
  }

  @Bean
  @JobScope
  public Step kafkaSimpleStep() {
    return stepBuilderFactory.get(STEP_NAME)
      .<String, String>chunk(3)
      .reader(kafkaSimpleItemReader.kafkaSimpleItemReader())
      .writer(kafkaSimpleItemWriter)
      .build();
  }

}
