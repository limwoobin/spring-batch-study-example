package com.example.batchexample.batch.classifier_v2;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.ArrayList;
import java.util.List;

public class ClassifierV2ItemReader implements ItemReader<Customer> {
  private List<Customer> items;
  private int index;

  public ClassifierV2ItemReader() {
    this.index = 0;
    this.items = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      Customer customer = new Customer("test-name" + i, "test-city" + i, i);
      items.add(customer);
    }
  }

  @Override
  public Customer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
    if (index == 10) {
      return null;
    }

    Customer item = items.get(index);
    index++;
    return item;
  }
}
