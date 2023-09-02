package com.example.batchexample;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.Map;

@Configuration
public class JobConfiguration {
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  public JobConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.stepBuilderFactory = stepBuilderFactory;
  }

  @Bean
  public Job job2() {
    return jobBuilderFactory.get("job2")
      .start(step1())
      .next(step2())
      .build();
  }

  @Bean
  public Step step1() {
    return stepBuilderFactory.get("step1")
      .tasklet(new Tasklet() {
        @Override
        public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
          JobParameters jobParameters = contribution.getStepExecution().getJobParameters();
          String name = jobParameters.getString("name");
          Long seq = jobParameters.getLong("seq");
          Date birth = jobParameters.getDate("birth");
          Double length = jobParameters.getDouble("length");

          Map<String, Object> params = chunkContext.getStepContext().getJobParameters();

          System.out.println("=====================");
          System.out.println("step1 was executed");
          System.out.println("=====================");
          return RepeatStatus.FINISHED;
        }
      }).build();
  }

  @Bean
  public Step step2() {
    return stepBuilderFactory.get("step2")
      .tasklet(new Tasklet() {
        @Override
        public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
          System.out.println("=====================");
          System.out.println("step2 was executed");
          System.out.println("=====================");
          return null;
        }
      }).build();
  }
}
