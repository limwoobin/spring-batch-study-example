package com.example.batchexample.batch.kafka;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

@Component
//@StepScope
public class KafkaSimpleItemReader {

  private final KafkaProperties kafkaProperties;

  public KafkaSimpleItemReader(KafkaProperties kafkaProperties) {
    this.kafkaProperties = kafkaProperties;
  }

  @Value("${spring.kafka.topics.dead-letter}")
  private String topic;

  public KafkaItemReader<String, String> kafkaSimpleItemReader() {
    Properties props = new Properties();
    props.putAll(kafkaProperties.buildConsumerProperties());
    props.put("group.id", "dlt-consumer-group");
//    props.put("isolation.level", "read_committed");
//    props.put("client.id", "dlt-consumer-group");

    KafkaItemReader<String, String> kafkaItemReader = new KafkaItemReaderBuilder<String, String>()
      .name("kafkaSimpleItemReader")
      .topic(topic)
      .partitions(0)
      .consumerProperties(props)
      .pollTimeout(Duration.ofSeconds(5L))
      .saveState(true)
      .build();

    kafkaItemReader.setPartitionOffsets(new HashMap<>());
    return kafkaItemReader;
  }

}
