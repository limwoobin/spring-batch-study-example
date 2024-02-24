package com.example.batchexample.batch.classifier_v2;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;

public class ZipCodeV2Classifier implements Classifier<Customer, ItemProcessor<Customer, Customer>> {
  private final ItemProcessor<Customer, Customer> oddProcessor;
  private final ItemProcessor<Customer, Customer> evenProcessor;

  public ZipCodeV2Classifier(ItemProcessor<Customer, Customer> oddProcessor, ItemProcessor<Customer, Customer> evenProcessor) {
    this.oddProcessor = oddProcessor;
    this.evenProcessor = evenProcessor;
  }

  @Override
  public ItemProcessor<Customer, Customer> classify(Customer classifiable) {
    if (classifiable.getAddressNumber() % 2 == 0) {
      return evenProcessor;
    }

    return oddProcessor;
  }
}
