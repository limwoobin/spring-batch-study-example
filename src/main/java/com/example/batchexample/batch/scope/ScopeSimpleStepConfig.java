package com.example.batchexample.batch.scope;

import com.example.batchexample.domain.Post;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ScopeSimpleStepConfig {
  public static final String STEP_NAME = "scopeSimpleStep";
  private static final int CHUNK_SIZE = 10;

  private final StepBuilderFactory stepBuilderFactory;
  private final ItemReader<Post> itemReader;

  public ScopeSimpleStepConfig(StepBuilderFactory stepBuilderFactory,
                               @Qualifier(value = "scopeItemReader") ItemReader<Post> itemReader) {
    this.stepBuilderFactory = stepBuilderFactory;
    this.itemReader = itemReader;
  }

//  @BeforeStep
//  public void beforeStep(StepExecution stepExecution) {
//    String result = "";
//
//    if (stepExecution == null) {
//      throw new NullPointerException("StepExecution is null.");
//    }
//
//    result = stepExecution.getJobParameters().getString("param");
//    System.out.println(result);
//  }

  @Bean
  @JobScope
  public Step scopeSimpleStep(@Value("#{jobParameters[param]}") String param) {
    System.out.println("step: " + param);

    return stepBuilderFactory.get(STEP_NAME)
      .<Post, Post>chunk(CHUNK_SIZE)
      .reader(itemReader)
      .writer(jpaItemWriter())
      .build();
  }

  private ItemWriter<Post> jpaItemWriter() {
    return items -> {
      for (Post item : items) {
        System.out.println(item.getId() + ", " + item.getTitle());
      }
    };
  }
}
