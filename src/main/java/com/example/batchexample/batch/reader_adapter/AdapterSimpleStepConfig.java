package com.example.batchexample.batch.reader_adapter;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdapterSimpleStepConfig {
  public static final String STEP_NAME = "adapterSimpleStep";

  private final StepBuilderFactory stepBuilderFactory;

  public AdapterSimpleStepConfig(StepBuilderFactory stepBuilderFactory) {
    this.stepBuilderFactory = stepBuilderFactory;
  }

  @Bean(name = STEP_NAME)
  public Step adapterSimpleStep() {
    return stepBuilderFactory.get(STEP_NAME)
      .<String, String>chunk(10)
      .reader(itemReaderAdapter())
      .writer(itemWriter())
      .build();
  }

  private ItemReaderAdapter<String> itemReaderAdapter() {
    ItemReaderAdapter<String> itemReaderAdapter = new ItemReaderAdapter<>();
    itemReaderAdapter.setTargetObject(new CustomService());
    itemReaderAdapter.setTargetMethod("adapterRead");

    return itemReaderAdapter;
  }

  private ItemWriter<? super Object> itemWriter() {
    return items -> {
      for (Object item: items) {
        System.out.println("adapter simple: " + item);
      }
    };
  }
}
