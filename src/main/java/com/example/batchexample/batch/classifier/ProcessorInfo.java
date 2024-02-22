package com.example.batchexample.batch.classifier;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessorInfo {
  private int id;

  public ProcessorInfo(int id) {
    this.id = id;
  }
}
