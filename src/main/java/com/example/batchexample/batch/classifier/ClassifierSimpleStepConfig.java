package com.example.batchexample.batch.classifier;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ClassifierSimpleStepConfig {
  public static final String STEP_NAME = "classifierSimpleStep";

  private final StepBuilderFactory stepBuilderFactory;

  public ClassifierSimpleStepConfig(StepBuilderFactory stepBuilderFactory) {
    this.stepBuilderFactory = stepBuilderFactory;
  }

  @Bean
  public Step classifierSimpleStep() {
    return stepBuilderFactory.get(STEP_NAME)
      .<ProcessorInfo, ProcessorInfo>chunk(2)
      .reader(new ItemReader<ProcessorInfo>() {
        int i = 0;

        @Override
        public ProcessorInfo read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
          i++;
          ProcessorInfo processorInfo = new ProcessorInfo(i);

          return i > 3 ? null : processorInfo;
        }
      })
      .processor(customItemProcessor())
      .writer(itemWriter())
      .build();
  }

  @Bean
  public ItemProcessor<ProcessorInfo, ProcessorInfo> customItemProcessor() {
    ClassifierCompositeItemProcessor<ProcessorInfo, ProcessorInfo> processor = new ClassifierCompositeItemProcessor<>();
    ProcessorClassifier<ProcessorInfo, ItemProcessor<?, ? extends ProcessorInfo>> classifier = new ProcessorClassifier<>();

    Map<Integer, ItemProcessor<ProcessorInfo, ProcessorInfo>> processorMap = new HashMap<>();
    processorMap.put(1, new CustomItemProcessor1());
    processorMap.put(2, new CustomItemProcessor2());
    processorMap.put(3, new CustomItemProcessor3());
    classifier.setProcessorMap(processorMap);
    processor.setClassifier(classifier);

    return processor;
  }

  @Bean
  public ItemWriter<? super ProcessorInfo> itemWriter() {
    return items -> {
      System.out.println(items.size());
    };
  }
}
