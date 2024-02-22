package com.example.batchexample.batch.classifier;


import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;

import java.util.HashMap;
import java.util.Map;

public class ProcessorClassifier<C, T> implements Classifier<C, T> {
  private Map<Integer, ItemProcessor<ProcessorInfo, ProcessorInfo>> processorMap = new HashMap<>();

  @Override
  public T classify(C classifiable) {
    ProcessorInfo processorInfo = (ProcessorInfo) classifiable;
    return (T) processorMap.get(processorInfo.getId());
  }

  public void setProcessorMap(Map<Integer, ItemProcessor<ProcessorInfo, ProcessorInfo>> processorMap) {
    this.processorMap = processorMap;
  }
}
