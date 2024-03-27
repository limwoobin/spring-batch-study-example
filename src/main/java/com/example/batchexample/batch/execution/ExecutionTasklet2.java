package com.example.batchexample.batch.execution;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
//@StepScope
public class ExecutionTasklet2 implements Tasklet {

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
    ExecutionContext stepExecutionContext = contribution.getStepExecution()
      .getExecutionContext();
    String stepValue = (String) stepExecutionContext.get("test");

    ExecutionContext jobExecutionContext = contribution.getStepExecution()
      .getJobExecution()
      .getExecutionContext();
    String jobValue = (String) jobExecutionContext.get("job-test");

    System.out.println("step value: " + stepValue);
    System.out.println("job value: " + jobValue);
    System.out.println("ExecutionTasklet 2 !!!");
    return RepeatStatus.FINISHED;
  }
}
