package com.example.batchexample.batch.classifier_v2;

import org.springframework.batch.item.ItemProcessor;

public class EvenClassifierItemProcessor implements ItemProcessor<Customer, Customer> {
  @Override
  public Customer process(Customer item) throws Exception {
    return item;
  }
}
