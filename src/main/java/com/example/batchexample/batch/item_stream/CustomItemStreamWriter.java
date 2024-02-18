package com.example.batchexample.batch.item_stream;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;

import java.util.List;

public class CustomItemStreamWriter implements ItemStreamWriter<String> {

  @Override
  public void open(ExecutionContext executionContext) throws ItemStreamException {
    System.out.println("Stream Writer open");
  }

  @Override
  public void update(ExecutionContext executionContext) throws ItemStreamException {
    System.out.println("Stream Writer update");
  }

  @Override
  public void close() throws ItemStreamException {
    System.out.println("Stream Writer closed");
  }

  @Override
  public void write(List<? extends String> items) throws Exception {
    items.forEach(System.out::println);
  }
}
