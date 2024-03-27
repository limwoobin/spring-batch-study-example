package com.example.batchexample.batch.execution;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class ExecutionTasklet implements Tasklet {

  @Value("#{stepExecutionContext['batch.data.source.init']}")
  private Boolean contextValue;

  @Value("#{stepExecutionContext['dirty']}")
  private Boolean contextValue2;

  @Value("#{stepExecutionContext['directory.filename']}")
  private String contextValue3;

  @BeforeStep
  public void beforeStep(StepExecution stepExecution) {
    String param = stepExecution.getJobParameters().getString("param");
    String name = stepExecution.getJobParameters().getString("name");
    Long timestamp = stepExecution.getJobParameters().getLong("timestamp");
    System.out.println("param: " + param);
    System.out.println("name: " + name);
    System.out.println("timestamp: " + timestamp);
  }

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
    ExecutionContext stepExecutionContext = contribution.getStepExecution()
      .getExecutionContext();
    stepExecutionContext.put("test", "test");

    ExecutionContext jobExecutionContext = contribution.getStepExecution()
        .getJobExecution()
        .getExecutionContext();

    jobExecutionContext.put("job-test", "job-test");

    System.out.println("ExecutionTasklet !!!");
    return RepeatStatus.FINISHED;
  }
}
