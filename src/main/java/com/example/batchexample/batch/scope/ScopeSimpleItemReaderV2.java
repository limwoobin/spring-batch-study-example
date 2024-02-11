package com.example.batchexample.batch.scope;

import com.example.batchexample.domain.Post;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
@StepScope
public class ScopeSimpleItemReaderV2 implements ItemReader<Post> {

  private final EntityManagerFactory entityManagerFactory;

  public ScopeSimpleItemReaderV2(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

  @Value("#{jobParameters[param]}")
  private String param;

  @BeforeStep
  public void beforeStep(StepExecution stepExecution) {
    String result = "";

    if (stepExecution == null) {
      throw new NullPointerException("StepExecution is null.");
    }

    result = stepExecution.getJobParameters().getString("param");
    System.out.println("beforeStep: " + result);
  }

  @Override
  public Post read() throws Exception {
    System.out.println("reader: " + param);
    return null;
  }
}
