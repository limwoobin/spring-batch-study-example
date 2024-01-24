package com.example.batchexample.batch.jdbc_paging;

import com.example.batchexample.domain.Post;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JdbcSimpleStepConfig {
  public static final String STEP_NAME = "jdbcSimpleStep";

  private final StepBuilderFactory stepBuilderFactory;
  private final DataSource dataSource;

  public JdbcSimpleStepConfig(StepBuilderFactory stepBuilderFactory,
                              DataSource dataSource) {
    this.stepBuilderFactory = stepBuilderFactory;
    this.dataSource = dataSource;
  }

  @Bean
  public Step jdbcSimpleStep() throws Exception {
    return stepBuilderFactory.get(STEP_NAME)
      .<Post, Post>chunk(1)
      .reader(jdbcPagingItemReader())
      .processor(itemProcessor())
      .writer(itemWriter())
      .build();
  }

  @Bean
  public JdbcPagingItemReader<Post> jdbcPagingItemReader() throws Exception {
    Map<String, Object> parameterValues = new HashMap<>();
    parameterValues.put("id", 200);

    return new JdbcPagingItemReaderBuilder<Post>()
      .pageSize(20)
      .fetchSize(20)
      .dataSource(dataSource)
      .rowMapper(new BeanPropertyRowMapper<>(Post.class))
      .queryProvider(createQueryProvider())
      .parameterValues(parameterValues)
      .name("jdbcPagingItemReader")
      .build();
  }

  @Bean
  public PagingQueryProvider createQueryProvider() throws Exception {
    SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
    queryProvider.setDataSource(dataSource);
    queryProvider.setSelectClause("id, title, author");
    queryProvider.setFromClause("from posts");
    queryProvider.setWhereClause("where id <= :id");

    Map<String, Order> sortKeys = new HashMap<>(1);
    sortKeys.put("id", Order.ASCENDING);

    queryProvider.setSortKeys(sortKeys);

    return queryProvider.getObject();
  }

  @Bean
  public ItemProcessor<Post, Post> itemProcessor() {
    return item -> {
      item.changeTitle("-jdbc-test");
      return item;
    };
  }

  @Bean
  public JdbcBatchItemWriter<Post> itemWriter() {
    return new JdbcBatchItemWriterBuilder<Post>()
      .dataSource(dataSource)
      .sql("update posts set title=:title where id=:id")
      .beanMapped()
      .assertUpdates(false)
      .build();
  }
}
