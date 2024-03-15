package com.example.batchexample.batch.custom_kafka;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomKafkaSimpleItemWriter implements ItemWriter<String> {
  @Override
  public void write(List<? extends String> items) {
    for (String item : items) {
      System.out.println("item: " + item);
    }
  }
}
