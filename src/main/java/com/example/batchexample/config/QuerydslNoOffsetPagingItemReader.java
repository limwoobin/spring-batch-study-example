package com.example.batchexample.config;

import com.example.batchexample.config.options.QuerydslNoOffsetOptions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.function.Function;

public class QuerydslNoOffsetPagingItemReader<T> extends QueryDslPagingItemReader<T> {
  private QuerydslNoOffsetOptions<T> options;

  private QuerydslNoOffsetPagingItemReader() {
    super();
    setName(ClassUtils.getShortName(QuerydslNoOffsetPagingItemReader.class));
  }

  public QuerydslNoOffsetPagingItemReader(EntityManagerFactory entityManagerFactory,
                                          int pageSize,
                                          QuerydslNoOffsetOptions<T> options,
                                          Function<JPAQueryFactory, JPAQuery<T>> queryFunction) {
    super(entityManagerFactory, pageSize, queryFunction);
    setName(ClassUtils.getShortName(QuerydslNoOffsetPagingItemReader.class));
    this.options = options;
  }

  @Override
  protected void doReadPage() {
    EntityTransaction tx = getTxOrNull();
    JPAQuery<T> query = createQuery().limit(getPageSize());

    initResults();
    fetchQuery(query, tx);
    resetCurrentIdIfNotLastPage();
  }

  @Override
  protected JPAQuery<T> createQuery() {
    JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
    JPAQuery<T> query = queryFunction.apply(queryFactory);
    options.initKeys(query, getPage());

    return options.createQuery(query, getPage());
  }

  private void resetCurrentIdIfNotLastPage() {
    if (isNotEmptyResults()) {
      options.resetCurrentId(getLastItem());
    }
  }

  private boolean isNotEmptyResults() {
    return !CollectionUtils.isEmpty(results) && results.get(0) != null;
  }

  private T getLastItem() {
    return results.get(results.size() - 1);
  }
}
