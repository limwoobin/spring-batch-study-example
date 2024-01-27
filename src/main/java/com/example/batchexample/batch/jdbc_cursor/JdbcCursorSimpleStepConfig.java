package com.example.batchexample.batch.jdbc_cursor;

import com.example.batchexample.domain.Post;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JdbcCursorSimpleStepConfig {
  public static final String STEP_NAME = "jdbcCursorSimpleStep";
  private static final int CHUNK_SIZE = 20;

  private final StepBuilderFactory stepBuilderFactory;
  private final DataSource dataSource;

  public JdbcCursorSimpleStepConfig(StepBuilderFactory stepBuilderFactory,
                                    DataSource dataSource) {
    this.stepBuilderFactory = stepBuilderFactory;
    this.dataSource = dataSource;
  }

  @Bean
  public Step jdbcCursorSimpleStep() {
    return stepBuilderFactory.get(STEP_NAME)
      .<Post, Post>chunk(CHUNK_SIZE)
      .reader(jdbcCursorItemReader())
      .processor(itemProcessor())
      .writer(itemWriter())
      .build();
  }

  private JdbcCursorItemReader<Post> jdbcCursorItemReader() {
    String sql = "select id, title, author from posts where id <= :id order by id asc";
    Map<String, Object> namedParameters = new HashMap<>();
    namedParameters.put("id", 50);

    return new JdbcCursorItemReaderBuilder<Post>()
      .fetchSize(CHUNK_SIZE)
      .dataSource(dataSource)
      .beanRowMapper(Post.class)
      .sql(NamedParameterUtils.substituteNamedParameters(sql, new MapSqlParameterSource(namedParameters)))
      .preparedStatementSetter(new ArgumentPreparedStatementSetter(NamedParameterUtils.buildValueArray(sql, namedParameters)))
      .name("jdbcCursorItemReader")
      .build();
  }

  private ItemProcessor<Post, Post> itemProcessor() {
    return item -> {
      item.changeTitle("hi");
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
