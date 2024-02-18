package com.example.batchexample.batch.item_stream;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ItemStreamStepConfig {
  public static final String STEP_NAME = "itemStreamStep";

  private final StepBuilderFactory stepBuilderFactory;

  public ItemStreamStepConfig(StepBuilderFactory stepBuilderFactory) {
    this.stepBuilderFactory = stepBuilderFactory;
  }

  @Bean
  public Step itemStreamStep() {
    return stepBuilderFactory.get(STEP_NAME)
      .<String, String>chunk(5)
      .reader(itemReader())
      .writer(itemWriter())
      .build();
  }

  public CustomItemStreamReader itemReader() {
    List<String> items = new ArrayList<>(10);

    for (int i = 0; i < 10; i++) {
      items.add(String.valueOf(i));
    }

    return new CustomItemStreamReader(items);
  }

  public ItemWriter<? super String> itemWriter() {
    return new CustomItemStreamWriter();
  }
}
