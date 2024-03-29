package com.example.batchexample.batch.query_dsl_paging;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslPagingJobConfig {
  private static final String JOB_NAME = "queryDslPagingJob";

  private final JobBuilderFactory jobBuilderFactory;
  private final Step queryDslPagingStep;

  public QueryDslPagingJobConfig(JobBuilderFactory jobBuilderFactory,
                                 Step queryDslPagingStep) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.queryDslPagingStep = queryDslPagingStep;
  }

  @Bean
  public Job queryDslPagingJob() {
    return jobBuilderFactory.get(JOB_NAME)
      .start(queryDslPagingStep)
      .build();
  }
}
