package com.example.batchexample.flow;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class FlowJobConfiguration {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  /**
   * flowStep1 이 성공하면 flowStep3, 실패하면 flowStep2
   * @return
   */
  @Bean
  public Job flowJob() {
    return jobBuilderFactory.get("flowJob")
      .start(flowStep1())
      .on("COMPLETED").to(flowStep3())
      .from(flowStep1())
      .on("FAILED").to(flowStep2())
      .end()
      .build();
  }

  @Bean
  public Step flowStep1() {
    return stepBuilderFactory.get("flowStep1")
      .tasklet((stepContribution, chunkContext) -> RepeatStatus.FINISHED).build();
  }

  @Bean
  public Step flowStep2() {
    return stepBuilderFactory.get("flowStep2")
      .tasklet((stepContribution, chunkContext) -> RepeatStatus.FINISHED).build();
  }

  @Bean
  public Step flowStep3() {
    return stepBuilderFactory.get("flowStep3")
      .tasklet((stepContribution, chunkContext) -> RepeatStatus.FINISHED).build();
  }
}
