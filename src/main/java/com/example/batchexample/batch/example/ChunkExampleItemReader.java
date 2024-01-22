package com.example.batchexample.batch.example;

import com.example.batchexample.domain.PostRepository;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ChunkExampleItemReader implements ItemReader<Long> {

  private final PostRepository postRepository;

  private Long idOffset;

  public ChunkExampleItemReader(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @BeforeStep
  public void beforeStep(StepExecution stepExecution) {
    this.idOffset = 1L;
  }

  @Override
  public Long read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
    if (idOffset == 5) {
      return null;
    }

    return idOffset++;
  }
}
