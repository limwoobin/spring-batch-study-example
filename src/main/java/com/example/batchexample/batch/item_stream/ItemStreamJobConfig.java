package com.example.batchexample.batch.item_stream;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemStreamJobConfig {
  private static final String JOB_NAME = "itemStreamJob";

  private final JobBuilderFactory jobBuilderFactory;
  private final Step itemStreamStep;

  public ItemStreamJobConfig(JobBuilderFactory jobBuilderFactory,
                             @Qualifier(value = ItemStreamStepConfig.STEP_NAME) Step itemStreamStep) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.itemStreamStep = itemStreamStep;
  }

  @Bean
  public Job itemStreamJob() {
    return jobBuilderFactory.get(JOB_NAME)
      .start(itemStreamStep)
      .build();
  }
}
