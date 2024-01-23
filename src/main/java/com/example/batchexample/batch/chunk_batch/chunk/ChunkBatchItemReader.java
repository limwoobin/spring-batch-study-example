package com.example.batchexample.batch.chunk_batch.chunk;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Component
public class ChunkBatchItemReader implements ItemReader<List<Long>> {

  private Long idOffset;

  @BeforeStep
  void beforeStep(StepExecution stepExecution) {
    this.idOffset = 0L;
  }

  @Override
  public List<Long> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
    if (idOffset > 10000) {
      return null;
    }

    return LongStream.range(idOffset, idOffset += 100)
      .boxed()
      .collect(Collectors.toList());
  }
}
