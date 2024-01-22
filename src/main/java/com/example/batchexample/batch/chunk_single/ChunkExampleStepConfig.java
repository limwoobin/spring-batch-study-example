package com.example.batchexample.batch.chunk_single;

import com.example.batchexample.batch.chunk_single.example.ChunkExampleItemProcessor;
import com.example.batchexample.batch.chunk_single.example.ChunkExampleItemReader;
import com.example.batchexample.batch.chunk_single.example.ChunkExampleItemWriter;
import com.example.batchexample.domain.Post;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChunkExampleStepConfig {

  public static final String STEP_NAME = "chunkExampleStep";
  private static final int CHUNK_SIZE = 100;

  private final StepBuilderFactory stepBuilderFactory;
  private final ChunkExampleItemReader chunkExampleItemReader;
  private final ChunkExampleItemProcessor chunkExampleItemProcessor;
  private final ChunkExampleItemWriter chunkExampleItemWriter;

  public ChunkExampleStepConfig(
    StepBuilderFactory stepBuilderFactory,
    ChunkExampleItemReader chunkExampleItemReader,
    ChunkExampleItemProcessor chunkExampleItemProcessor,
    ChunkExampleItemWriter chunkExampleItemWriter) {
    this.stepBuilderFactory = stepBuilderFactory;
    this.chunkExampleItemReader = chunkExampleItemReader;
    this.chunkExampleItemProcessor = chunkExampleItemProcessor;
    this.chunkExampleItemWriter = chunkExampleItemWriter;
  }

  @Bean
  public Step chunkExampleStep() {
    return stepBuilderFactory.get(STEP_NAME)
      .<Long, Post>chunk(3)
      .reader(chunkExampleItemReader)
      .processor(chunkExampleItemProcessor)
      .writer(chunkExampleItemWriter)
      .build();
  }
}
