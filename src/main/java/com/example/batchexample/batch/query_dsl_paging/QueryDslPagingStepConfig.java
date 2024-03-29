package com.example.batchexample.batch.query_dsl_paging;

import com.example.batchexample.config.QueryDslPagingItemReader;
import com.example.batchexample.domain.Post;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

import static com.example.batchexample.domain.QPost.post;


@Configuration
public class QueryDslPagingStepConfig {
  public static final String STEP_NAME = "queryDslPagingStep";
  public static final int CHUNK_SIZE = 1000;

  private final StepBuilderFactory stepBuilderFactory;
  private final EntityManagerFactory emf;
  private final JPAQueryFactory jpaQueryFactory;

  public QueryDslPagingStepConfig(StepBuilderFactory stepBuilderFactory,
                                  EntityManagerFactory emf,
                                  JPAQueryFactory jpaQueryFactory) {
    this.stepBuilderFactory = stepBuilderFactory;
    this.emf = emf;
    this.jpaQueryFactory = jpaQueryFactory;
  }

  @Bean
  public Step queryDslPagingStep() {
    return stepBuilderFactory.get(STEP_NAME)
      .tasklet(null)
      .build();
  }

  public QueryDslPagingItemReader<Post> queryDslPagingItemReader() {
    JPAQuery<Post> jpaQuery = jpaQueryFactory.selectFrom(post)
      .where(post.id.lt(10));

    return new QueryDslPagingItemReader<>(emf, CHUNK_SIZE, queryFactory -> jpaQuery);
  }
}
