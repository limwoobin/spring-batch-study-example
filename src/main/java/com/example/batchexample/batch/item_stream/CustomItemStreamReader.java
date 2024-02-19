package com.example.batchexample.batch.item_stream;

import org.springframework.batch.item.*;

import java.util.List;

public class CustomItemStreamReader implements ItemStreamReader<String> {
  private final List<String> items;
  private int index = -1;
  private boolean restart = false;

  public CustomItemStreamReader(List<String> items) {
    this.items = items;
    this.index = 0;
  }

  @Override
  public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
    String item = null;

    if (this.index < this.items.size()) {
      item = this.items.get(index);
      index++;
    }

    if (this.index == 6 && !restart) {
      throw new RuntimeException("Restart is required");
    }

    return item;
  }

  @Override
  public void open(ExecutionContext executionContext) throws ItemStreamException {
    if (executionContext.containsKey("index")) {
      this.index = executionContext.getInt("index");
      this.restart = true;
      return;
    }

    this.index = 0;
    executionContext.put("index", index);
  }

  @Override
  public void update(ExecutionContext executionContext) throws ItemStreamException {
    executionContext.put("index", index);
  }

  @Override
  public void close() throws ItemStreamException {
    System.out.println("Stream Reader Closed.");
  }
}
