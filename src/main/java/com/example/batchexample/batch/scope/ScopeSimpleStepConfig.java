package com.example.batchexample.batch.scope;

import com.example.batchexample.domain.Post;
import org.springframework.batch.core.Step;
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
  private final ScopeSimpleItemReaderV2 scopeSimpleItemReaderV2;

  public ScopeSimpleStepConfig(StepBuilderFactory stepBuilderFactory,
                               @Qualifier(value = "scopeItemReader") ItemReader<Post> itemReader,
                               ScopeSimpleItemReaderV2 scopeSimpleItemReaderV2) {
    this.stepBuilderFactory = stepBuilderFactory;
    this.itemReader = itemReader;
    this.scopeSimpleItemReaderV2 = scopeSimpleItemReaderV2;
  }

  @Bean
  @JobScope
  public Step scopeSimpleStep(@Value("#{jobParameters[param]}") String param) {
    System.out.println("step: " + param);

    return stepBuilderFactory.get(STEP_NAME)
      .<Post, Post>chunk(CHUNK_SIZE)
//      .reader(itemReader)
      .reader(scopeSimpleItemReaderV2)
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
