package com.example.batchexample.batch.chunk_batch;

import com.example.batchexample.batch.chunk_batch.chunk.ChunkBatchItemProcessor;
import com.example.batchexample.batch.chunk_batch.chunk.ChunkBatchItemReader;
import com.example.batchexample.batch.chunk_batch.chunk.ChunkBatchItemWriter;
import com.example.batchexample.domain.Post;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChunkBatchStepConfig {
  public static final String STEP_NAME = "chunkBatchStep";

  private final StepBuilderFactory stepBuilderFactory;
  private final ChunkBatchItemReader chunkBatchItemReader;
  private final ChunkBatchItemProcessor chunkBatchItemProcessor;
  private final ChunkBatchItemWriter chunkBatchItemWriter;

  public ChunkBatchStepConfig(StepBuilderFactory stepBuilderFactory,
                              ChunkBatchItemReader chunkBatchItemReader,
                              ChunkBatchItemProcessor chunkBatchItemProcessor,
                              ChunkBatchItemWriter chunkBatchItemWriter) {
    this.stepBuilderFactory = stepBuilderFactory;
    this.chunkBatchItemReader = chunkBatchItemReader;
    this.chunkBatchItemProcessor = chunkBatchItemProcessor;
    this.chunkBatchItemWriter = chunkBatchItemWriter;
  }

  @Bean
  public Step chunkBatchStep() {
    return stepBuilderFactory.get(STEP_NAME)
      .<List<Long>, List<Post>>chunk(2)
      .reader(chunkBatchItemReader)
      .processor(chunkBatchItemProcessor)
      .writer(chunkBatchItemWriter)
      .build();
  }
}
