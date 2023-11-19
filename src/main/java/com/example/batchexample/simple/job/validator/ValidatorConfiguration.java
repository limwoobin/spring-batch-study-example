package com.example.batchexample.simple.job.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ValidatorConfiguration {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Bean
  public Job batchJob() {
    return jobBuilderFactory.get("validatorBatchJob")
      .start(validatorStep1())
      .next(validatorStep2())
//      .validator(new CustomJobParameterValidator())
      .validator(new DefaultJobParametersValidator(new String[]{"name", "date"}, new String[]{"count"}))
      .build();
  }

  @Bean
  public Step validatorStep1() {
    return stepBuilderFactory.get("validatorStep1")
      .tasklet((stepContribution, chunkContext) -> RepeatStatus.FINISHED).build();
  }

  @Bean
  public Step validatorStep2() {
    return stepBuilderFactory.get("validatorStep2")
      .tasklet((stepContribution, chunkContext) -> RepeatStatus.FINISHED).build();
  }
}
