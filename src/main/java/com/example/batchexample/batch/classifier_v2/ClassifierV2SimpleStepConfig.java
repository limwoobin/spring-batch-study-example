package com.example.batchexample.batch.classifier_v2;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClassifierV2SimpleStepConfig {
  public static final String STEP_NAME = "classifierV2SimpleStep";

  private final StepBuilderFactory stepBuilderFactory;

  public ClassifierV2SimpleStepConfig(StepBuilderFactory stepBuilderFactory) {
    this.stepBuilderFactory = stepBuilderFactory;
  }

  @Bean
  public Step classifierV2SimpleStep() {
    return stepBuilderFactory.get(STEP_NAME)
      .<Customer, Customer>chunk(2)
      .reader(new ClassifierV2ItemReader())
      .processor(customItemProcessor())
//      .processor(customItemProcessor2())
      .writer(System.out::println)
      .build();
  }

  private ItemProcessor<Customer, Customer> customItemProcessor() {
    ZipCodeClassifier<Customer, ItemProcessor<?, ? extends Customer>> classifier = new ZipCodeClassifier<>(
      new OddClassifierItemProcessor(),
      new EvenClassifierItemProcessor()
    );

    ClassifierCompositeItemProcessor<Customer, Customer> customProcessor = new ClassifierCompositeItemProcessor<>();
    customProcessor.setClassifier(classifier);
    return customProcessor;
  }

  private ItemProcessor<Customer, Customer> customItemProcessor2() {
    Classifier classifier = new ZipCodeV2Classifier(
      new OddClassifierItemProcessor(),
      new EvenClassifierItemProcessor()
    );

    ClassifierCompositeItemProcessor<Customer, Customer> customProcessor = new ClassifierCompositeItemProcessor<>();
    customProcessor.setClassifier(classifier);
    return customProcessor;
  }
}
