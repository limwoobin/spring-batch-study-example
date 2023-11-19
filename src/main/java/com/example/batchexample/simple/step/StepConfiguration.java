package com.example.batchexample.step;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StepConfiguration {
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Bean
  public Job stepBatchJob() {
    return jobBuilderFactory.get("stepBatchJob")
      .start(exampleStep1())
      .next(exampleStep2())
      .next(exampleStep3())
      .build();
  }

  @Bean
  public Step exampleStep1() {
    return stepBuilderFactory.get("exampleStep1")
      .tasklet((stepContribution, chunkContext) -> RepeatStatus.FINISHED)
      .build();
  }

  @Bean
  public Step exampleStep2() {
    return stepBuilderFactory.get("exampleStep2")
      .<String, String>chunk(3)
//      .reader()
//      .processor()
//      .writer()
      .build();
  }

  @Bean
  public Step exampleStep3() {
    return stepBuilderFactory.get("exampleStep3")
      .partitioner(exampleStep1())
      .gridSize(2)
      .build();
  }

//  @Bean
//  public Step exampleStep4() {
//    return stepBuilderFactory.get("exampleStep4")
//      .job(stepBatchJob())
//      .build();
//  }

  @Bean
  public Step exampleStep5() {
    return stepBuilderFactory.get("exampleStep5")
      .flow(flow())
      .build();
  }

  @Bean
  public Flow flow() {
    FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow");
    flowBuilder.start(exampleStep2()).end();
    return flowBuilder.build();
  }
}
