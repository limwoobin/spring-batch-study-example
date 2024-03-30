package com.example.batchexample.config;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

public class QueryDslPagingItemReader<T> extends AbstractPagingItemReader<T> {

  protected EntityManagerFactory entityManagerFactory;
  protected EntityManager entityManager;
  protected final Map<String, Object> jpaPropertyMap = new HashMap<>();
  protected Function<JPAQueryFactory, JPAQuery<T>> queryFunction;
  protected boolean transacted = true;//default value

  protected QueryDslPagingItemReader() {
    setName(ClassUtils.getShortName(QueryDslPagingItemReader.class));
  }

  public QueryDslPagingItemReader(EntityManagerFactory entityManagerFactory,
                                  int pageSize,
                                  Function<JPAQueryFactory, JPAQuery<T>> queryFunction) {
    this();
    this.entityManagerFactory = entityManagerFactory;
    this.queryFunction = queryFunction;
    setPageSize(pageSize);
  }

  /**
   * By default (true) the EntityTransaction will be started and committed around the read.
   * Can be overridden (false) in cases where the JPA implementation doesn't support a
   * particular transaction.  (e.g. Hibernate with a JTA transaction).  NOTE: may cause
   * problems in guaranteeing the object consistency in the EntityManagerFactory.
   *
   * @param transacted indicator
   */
  public void setTransacted(boolean transacted) {
    this.transacted = transacted;
  }

  @Override
  protected void doOpen() throws Exception {
    super.doOpen();

    entityManager = entityManagerFactory.createEntityManager(jpaPropertyMap);
    if (entityManager == null) {
      throw new DataAccessResourceFailureException("Unable to obtain an EntityManager");
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  protected void doReadPage() {
    EntityTransaction tx = getTxOrNull();

    JPAQuery<T> query = createQuery()
      .offset((long) getPage() * getPageSize())
      .limit(getPageSize());

    initResults();

    fetchQuery(query, tx);
  }

  protected void clearIfTransacted() {
    if (transacted) {
      entityManager.clear();
    }
  }

  protected void initResults() {
    if (CollectionUtils.isEmpty(results)) {
      results = new CopyOnWriteArrayList<>();
    } else {
      results.clear();
    }
  }

  protected EntityTransaction getTxOrNull() {
    if (transacted) {
      EntityTransaction tx = entityManager.getTransaction();
      tx.begin();

      entityManager.flush();
      entityManager.clear();
      return tx;
    }

    return null;
  }


  protected JPAQuery<T> createQuery() {
    JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
    return queryFunction.apply(queryFactory);
  }

  protected void fetchQuery(JPAQuery<T> query, EntityTransaction tx) {
    if (transacted) {
      results.addAll(query.fetch());
      if (tx != null) {
        tx.commit();
      }
    } else {
      List<T> queryResult = query.fetch();
      for (T entity : queryResult) {
        entityManager.detach(entity);
        results.add(entity);
      }
    }
  }

  @Override
  protected void doJumpToPage(int itemIndex) {
  }

  @Override
  protected void doClose() throws Exception {
    entityManager.close();
    super.doClose();
  }

}
