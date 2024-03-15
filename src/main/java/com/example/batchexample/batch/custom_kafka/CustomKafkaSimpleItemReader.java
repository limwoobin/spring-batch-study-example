package com.example.batchexample.batch.custom_kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

@Component
public class CustomKafkaSimpleItemReader {
  private final KafkaProperties kafkaProperties;

  public CustomKafkaSimpleItemReader(KafkaProperties kafkaProperties) {
    this.kafkaProperties = kafkaProperties;
  }

  @Value("${spring.kafka.topics.dead-letter}")
  private String topic;

  public CustomKafkaItemReader<String, String> customKafkaItemReader() {
    Properties props = new Properties();
    props.putAll(kafkaProperties.buildConsumerProperties());
    props.put("group.id", "dlt-consumer-group");

    CustomKafkaItemReader<String, String> kafkaItemReader = new CustomKafkaItemReaderBuilder<String, String>()
      .name("customKafkaSimpleItemReader")
      .topic(topic)
      .partitions(0)
      .consumerProperties(props)
      .pollTimeout(Duration.ofSeconds(5L))
      .build();

    kafkaItemReader.setPartitionOffsets(new HashMap<>());
    return kafkaItemReader;
  }

}
