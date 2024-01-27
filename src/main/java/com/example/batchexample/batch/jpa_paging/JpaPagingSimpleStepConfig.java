package com.example.batchexample.batch.jpa_paging;

import com.example.batchexample.domain.Post;
import com.example.batchexample.domain.PostRepository;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManagerFactory;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class JpaPagingSimpleStepConfig {
  public static final String STEP_NAME = "jpaPagingSimpleStep";
  private static final int CHUNK_SIZE = 10;

  private final StepBuilderFactory stepBuilderFactory;
  private final EntityManagerFactory entityManagerFactory;
  private final PostRepository postRepository;

  public JpaPagingSimpleStepConfig(StepBuilderFactory stepBuilderFactory,
                                   EntityManagerFactory entityManagerFactory,
                                   PostRepository postRepository) {
    this.stepBuilderFactory = stepBuilderFactory;
    this.entityManagerFactory = entityManagerFactory;
    this.postRepository = postRepository;
  }

  @Bean
  public Step jpaPagingSimpleStep() {
    return stepBuilderFactory.get(STEP_NAME)
      .<Post, Post>chunk(CHUNK_SIZE)
      .reader(jpaPagingItemReader())
      .processor(itemProcessor())
      .writer(itemWriter())
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

  private ItemReader<Post> repositoryItemReader() {
    return new RepositoryItemReaderBuilder<Post>()
      .repository(postRepository)
      .name("repositoryItemReader")
      .pageSize(CHUNK_SIZE)
      .methodName("findAll")
      .saveState(false)
      .arguments(List.of(50L))
      .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
      .build();
  }

  private ItemProcessor<Post, Post> itemProcessor() {
    return item -> {
      item.changeTitle("jpa paging");
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
}
