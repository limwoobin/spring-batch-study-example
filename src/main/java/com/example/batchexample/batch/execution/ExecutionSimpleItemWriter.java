package com.example.batchexample.batch.execution;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExecutionSimpleItemWriter implements ItemWriter<Long> {

  @BeforeStep
  void before(StepExecution stepExecution) {
    ExecutionContext executionContext = stepExecution.getExecutionContext();
    String value = (String) executionContext.get("test");
    String value2 = executionContext.getString("test");

    System.out.println("value: " + value);
    System.out.println("value2: " + value2);
  }

  @Override
  public void write(List<? extends Long> items) throws Exception {
    System.out.println(items);
  }
}
