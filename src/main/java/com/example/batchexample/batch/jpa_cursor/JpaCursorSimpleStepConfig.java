package com.example.batchexample.batch.jpa_cursor;

import com.example.batchexample.domain.Post;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JpaCursorSimpleStepConfig {
  public static final String STEP_NAME = "jpaCursorSimpleStep";
  private static final int CHUNK_SIZE = 20;

  private final StepBuilderFactory stepBuilderFactory;
  private final EntityManagerFactory entityManagerFactory;

  public JpaCursorSimpleStepConfig(StepBuilderFactory stepBuilderFactory,
                                   EntityManagerFactory entityManagerFactory) {
    this.stepBuilderFactory = stepBuilderFactory;
    this.entityManagerFactory = entityManagerFactory;
  }

  @Bean
  public Step jpaCursorSimpleStep() {
    return stepBuilderFactory.get(STEP_NAME)
      .<Post, Post>chunk(CHUNK_SIZE)
      .reader(jpaCursorItemReader())
      .processor(itemProcessor())
      .writer(itemWriter())
//      .writer(jpaItemWriter())
      .build();
  }

  private JpaCursorItemReader<Post> jpaCursorItemReader() {
    Map<String, Object> parameterValues = new HashMap<>();
    parameterValues.put("id", 50L);

    return new JpaCursorItemReaderBuilder<Post>()
      .name("jpaCursorItemReader")
      .entityManagerFactory(entityManagerFactory)
      .queryString("select p from posts p where p.id <= :id order by p.id asc")
      .parameterValues(parameterValues)
//      .maxItemCount(CHUNK_SIZE) 조회할 최대 item 수
      .currentItemCount(0) // 조회 item 의 시작 지점
      .build();
  }

  private ItemProcessor<Post, Post> itemProcessor() {
    return item -> {
      item.changeTitle("jpa cursor");
      return item;
    };
  }

  private ItemWriter<Post> itemWriter() {
    return items -> {
      for (Post item : items) {
        System.out.println(item.getId() + ", " + item.getTitle());
      }
    };
  }

  private JpaItemWriter<Post> jpaItemWriter() {
    JpaItemWriter<Post> jpaItemWriter = new JpaItemWriter<>();
    jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
    return jpaItemWriter;
  }
}
