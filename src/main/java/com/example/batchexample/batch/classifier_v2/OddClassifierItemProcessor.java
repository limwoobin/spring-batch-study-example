package com.example.batchexample.batch.classifier_v2;

import org.springframework.batch.item.ItemProcessor;

public class OddClassifierItemProcessor implements ItemProcessor<Customer, Customer> {
  @Override
  public Customer process(Customer item) throws Exception {
    String upperCity = item.getCity().toUpperCase();
    String upperName = item.getName().toUpperCase();

    item.setCity(upperCity);
    item.setName(upperName);
    return item;
  }
}
