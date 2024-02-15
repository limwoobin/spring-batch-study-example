package com.example.batchexample.batch.reader_adapter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdapterSimpleJobConfig {
  private static final String JOB_NAME = "adapterSimpleJob";

  private final JobBuilderFactory jobBuilderFactory;
  private final Step adapterSimpleStep;

  public AdapterSimpleJobConfig(JobBuilderFactory jobBuilderFactory, Step adapterSimpleStep) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.adapterSimpleStep = adapterSimpleStep;
  }

  @Bean(name = JOB_NAME)
  public Job adapterSimpleJob() {
    return jobBuilderFactory.get(JOB_NAME)
      .start(adapterSimpleStep)
      .build();
  }
}
