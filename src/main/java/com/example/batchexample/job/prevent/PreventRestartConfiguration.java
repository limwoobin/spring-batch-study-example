package com.example.batchexample.job.prevent;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PreventRestartConfiguration {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Bean
  public Job preventBatchJob() {
    return jobBuilderFactory.get("validatorBatchJob")
      .start(preventStep1())
      .next(preventStep2())
      .preventRestart()
      .build();
  }

  @Bean
  public Step preventStep1() {
    return stepBuilderFactory.get("preventStep1")
      .tasklet((stepContribution, chunkContext) -> RepeatStatus.FINISHED).build();
  }

  @Bean
  public Step preventStep2() {
    return stepBuilderFactory.get("preventStep2")
      .tasklet((stepContribution, chunkContext) -> RepeatStatus.FINISHED).build();
  }
}
