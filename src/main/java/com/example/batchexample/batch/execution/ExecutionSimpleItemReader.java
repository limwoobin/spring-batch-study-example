package com.example.batchexample.batch.execution;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.*;
import org.springframework.stereotype.Component;

@Component
public class ExecutionSimpleItemReader implements ItemReader<Long> {
  private Long count = 0L;

  @BeforeStep
  public void beforeStep(StepExecution stepExecution) {
    count = 0L;
    String param = stepExecution.getJobParameters().getString("param");
    String name = stepExecution.getJobParameters().getString("name");
    Long timestamp = stepExecution.getJobParameters().getLong("timestamp");
    System.out.println("param: " + param);
    System.out.println("name: " + name);
    System.out.println("timestamp: " + timestamp);

    ExecutionContext executionContext = stepExecution.getExecutionContext();
    executionContext.put("test", "test");
  }

  @Override
//  @StepScope
  public Long read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
    if (count > 3) {
      return null;
    }

    count++;
    return count;
  }

}
