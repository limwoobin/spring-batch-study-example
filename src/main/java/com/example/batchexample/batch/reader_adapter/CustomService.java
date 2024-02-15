package com.example.batchexample.batch.reader_adapter;

public class CustomService {
  private int cnt;

  public CustomService() {
    cnt = 0;
  }

  public String adapterRead() {
    if (cnt > 50) {
      return null;
    }

    return "item-" + cnt++;
  }
}
