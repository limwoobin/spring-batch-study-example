package com.example.batchexample.batch.compisite_job;

import com.example.batchexample.domain.Post;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.batch.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CompositeSimpleStepConfig {
  public static final String STEP_NAME = "compositeSimpleStep";
  private static final int CHUNK_SIZE = 10;

  private final StepBuilderFactory stepBuilderFactory;
  private final EntityManagerFactory entityManagerFactory;

  public CompositeSimpleStepConfig(StepBuilderFactory stepBuilderFactory,
                                   EntityManagerFactory entityManagerFactory) {
    this.stepBuilderFactory = stepBuilderFactory;
    this.entityManagerFactory = entityManagerFactory;
  }

  @Bean
  public Step compositeSimpleStep() {
    return stepBuilderFactory.get(STEP_NAME)
      .<Post, String >chunk(10)
      .reader(jpaPagingItemReader())
      .processor(compositeItemProcessor())
      .writer(compositeItemWriter())
      .build();
  }

  private JpaPagingItemReader<Post> jpaPagingItemReader() {
    Map<String, Object> parameterValues = new HashMap<>();
    parameterValues.put("id", 50L);

    return new JpaPagingItemReaderBuilder<Post>()
      .name("jpaPagingItemReader")
      .entityManagerFactory(entityManagerFactory)
      .pageSize(CHUNK_SIZE)
      .queryString("select p from posts p where p.id <= :id order by p.id asc")
      .parameterValues(parameterValues)
      .build();
  }

  private CompositeItemProcessor<Post, String> compositeItemProcessor() {
    return new CompositeItemProcessorBuilder<Post, String>()
      .delegates(processor1(), processor2())
      .build();
  }

  private ItemProcessor<Post, String> processor1() {
    return Post::getTitle;
  }

  private ItemProcessor<String, String> processor2() {
    return item -> {
      item += "subfix";
      return item;
    };
  }

  private CompositeItemWriter<String> compositeItemWriter() {
    return new CompositeItemWriterBuilder<String>()
      .delegates(writer1(), writer2())
      .build();
  }

  private ItemWriter<String> writer1() {
    return items -> {
      for (String item : items) {
        System.out.println("writer1=" + item);
      }
    };
  }

  private ItemWriter<String> writer2() {
    return items -> {
      for (String item : items) {
        System.out.println("writer2=" + item);
      }
    };
  }
}
