package com.example.batchexample.batch.custom_kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.*;

public class CustomKafkaItemReaderBuilder<K, V> {
  private Properties consumerProperties;
  private String topic;
  private List<Integer> partitions = new ArrayList<>();
  private Map<TopicPartition, Long> partitionOffsets;
  private Duration pollTimeout = Duration.ofSeconds(30L);
  private boolean saveState = true;
  private String name;

  public CustomKafkaItemReaderBuilder<K, V> saveState(boolean saveState) {
    this.saveState = saveState;
    return this;
  }

  public CustomKafkaItemReaderBuilder<K, V> name(String name) {
    this.name = name;
    return this;
  }

  public CustomKafkaItemReaderBuilder<K, V> consumerProperties(Properties consumerProperties) {
    this.consumerProperties = consumerProperties;
    return this;
  }

  public CustomKafkaItemReaderBuilder<K, V> partitions(Integer... partitions) {
    return partitions(Arrays.asList(partitions));
  }

  public CustomKafkaItemReaderBuilder<K, V> partitions(List<Integer> partitions) {
    this.partitions = partitions;
    return this;
  }

  public CustomKafkaItemReaderBuilder<K, V> partitionOffsets(Map<TopicPartition, Long> partitionOffsets) {
    this.partitionOffsets = partitionOffsets;
    return this;
  }

  public CustomKafkaItemReaderBuilder<K, V> topic(String topic) {
    this.topic = topic;
    return this;
  }

  public CustomKafkaItemReaderBuilder<K, V> pollTimeout(Duration pollTimeout) {
    this.pollTimeout = pollTimeout;
    return this;
  }

  public CustomKafkaItemReader<K, V> build() {
    if (this.saveState) {
      Assert.hasText(this.name, "A name is required when saveState is set to true");
    }
    Assert.notNull(consumerProperties, "Consumer properties must not be null");
    Assert.isTrue(consumerProperties.containsKey(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG),
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG + " property must be provided");
    Assert.isTrue(consumerProperties.containsKey(ConsumerConfig.GROUP_ID_CONFIG),
      ConsumerConfig.GROUP_ID_CONFIG + " property must be provided");
    Assert.isTrue(consumerProperties.containsKey(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG),
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG + " property must be provided");
    Assert.isTrue(consumerProperties.containsKey(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG),
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG + " property must be provided");
    Assert.hasLength(topic, "Topic name must not be null or empty");
    Assert.notNull(pollTimeout, "pollTimeout must not be null");
    Assert.isTrue(!pollTimeout.isZero(), "pollTimeout must not be zero");
    Assert.isTrue(!pollTimeout.isNegative(), "pollTimeout must not be negative");
    Assert.isTrue(!partitions.isEmpty(), "At least one partition must be provided");

    CustomKafkaItemReader<K, V> reader = new CustomKafkaItemReader<>(this.consumerProperties, this.topic, this.partitions);
    reader.setPollTimeout(this.pollTimeout);
    reader.setSaveState(this.saveState);
    reader.setName(this.name);
    reader.setPartitionOffsets(this.partitionOffsets);
    return reader;
  }
}
