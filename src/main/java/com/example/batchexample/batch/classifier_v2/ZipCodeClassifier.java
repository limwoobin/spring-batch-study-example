package com.example.batchexample.batch.classifier_v2;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;

public class ZipCodeClassifier<C, T> implements Classifier<C, T> {
  private final ItemProcessor<Customer, Customer> oddProcessor;
  private final ItemProcessor<Customer, Customer> evenProcessor;

  public ZipCodeClassifier(ItemProcessor<Customer, Customer> oddProcessor, ItemProcessor<Customer, Customer> evenProcessor) {
    this.oddProcessor = oddProcessor;
    this.evenProcessor = evenProcessor;
  }

  @Override
  public T classify(C classifiable) {
    Customer customer = (Customer) classifiable;

    if (customer.getAddressNumber() % 2 == 0) {
      return (T) evenProcessor;
    }

    return (T) oddProcessor;
  }
}
