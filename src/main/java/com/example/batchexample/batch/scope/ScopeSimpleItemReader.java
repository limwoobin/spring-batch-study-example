package com.example.batchexample.batch.scope;

import com.example.batchexample.domain.Post;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;


@Component
public class ScopeSimpleItemReader {
  private final EntityManagerFactory entityManagerFactory;

  public ScopeSimpleItemReader(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

  @Bean
  @StepScope
  public JpaPagingItemReader<Post> scopeItemReader(@Value("#{jobParameters[param]}") String param) {
    System.out.println("reader: " + param);
    Map<String, Object> parameterValues = new HashMap<>();
    parameterValues.put("id", 20L);

    return new JpaPagingItemReaderBuilder<Post>()
      .name("jpaPagingItemReader")
      .entityManagerFactory(entityManagerFactory)
      .pageSize(10)
      .queryString("select p from posts p where p.id <= :id order by p.id asc")
      .parameterValues(parameterValues)
      .build();
  }
}
