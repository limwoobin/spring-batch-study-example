package com.example.batchexample.batch.jpa_paging;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaPagingSimpleJobConfig {
  private static final String JOB_NAME = "jpaPagingSimpleJob";

  private final JobBuilderFactory jobBuilderFactory;
  private final Step jpaPagingSimpleStep;

  public JpaPagingSimpleJobConfig(JobBuilderFactory jobBuilderFactory,
                                  @Qualifier(value = JpaPagingSimpleStepConfig.STEP_NAME) Step jpaPagingSimpleStep) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.jpaPagingSimpleStep = jpaPagingSimpleStep;
  }

  @Bean
  public Job jpaPagingSimpleJob() {
    return jobBuilderFactory.get(JOB_NAME)
      .start(jpaPagingSimpleStep)
      .build();
  }
}
