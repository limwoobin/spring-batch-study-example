package com.example.batchexample.batch.custom_kafka;

import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.support.AbstractItemStreamItemReader;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.*;

public class CustomKafkaItemReader<K, V> extends AbstractItemStreamItemReader<V> {
  private static final String TOPIC_PARTITION_OFFSETS = "topic.partition.offsets";
  private static final long DEFAULT_POLL_TIMEOUT = 30L;
  private List<TopicPartition> topicPartitions;
  private Map<TopicPartition, Long> partitionOffsets;
  private KafkaConsumer<K, V> kafkaConsumer;
  private Properties consumerProperties;
  private Iterator<ConsumerRecord<K, V>> consumerRecords;
  private Duration pollTimeout = Duration.ofSeconds(DEFAULT_POLL_TIMEOUT);
  @Getter
  private boolean saveState = true;

  public CustomKafkaItemReader(Properties consumerProperties, String topicName, Integer... partitions) {
    this(consumerProperties, topicName, Arrays.asList(partitions));
  }

  public CustomKafkaItemReader(Properties consumerProperties, String topicName, List<Integer> partitions) {
    Assert.notNull(consumerProperties, "Consumer properties must not be null");
    Assert.isTrue(consumerProperties.containsKey(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG),
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG + " property must be provided");
    Assert.isTrue(consumerProperties.containsKey(ConsumerConfig.GROUP_ID_CONFIG),
      ConsumerConfig.GROUP_ID_CONFIG + " property must be provided");
    Assert.isTrue(consumerProperties.containsKey(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG),
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG + " property must be provided");
    Assert.isTrue(consumerProperties.containsKey(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG),
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG + " property must be provided");
    this.consumerProperties = consumerProperties;
    Assert.hasLength(topicName, "Topic name must not be null or empty");
    Assert.isTrue(!partitions.isEmpty(), "At least one partition must be provided");
    this.topicPartitions = new ArrayList<>();
    for (Integer partition : partitions) {
      this.topicPartitions.add(new TopicPartition(topicName, partition));
    }
  }

  public void setPollTimeout(Duration pollTimeout) {
    Assert.notNull(pollTimeout, "pollTimeout must not be null");
    Assert.isTrue(!pollTimeout.isZero(), "pollTimeout must not be zero");
    Assert.isTrue(!pollTimeout.isNegative(), "pollTimeout must not be negative");
    this.pollTimeout = pollTimeout;
  }

  public void setSaveState(boolean saveState) {
    this.saveState = saveState;
  }

  public void setPartitionOffsets(Map<TopicPartition, Long> partitionOffsets) {
    this.partitionOffsets = partitionOffsets;
  }

  @Override
  public void open(ExecutionContext executionContext) {
    this.kafkaConsumer = new KafkaConsumer<>(this.consumerProperties);
    if (this.partitionOffsets == null) {
      this.partitionOffsets = new HashMap<>();
      for (TopicPartition topicPartition : this.topicPartitions) {
        this.partitionOffsets.put(topicPartition, 0L);
      }
    }
    if (this.saveState && executionContext.containsKey(TOPIC_PARTITION_OFFSETS)) {
      Map<TopicPartition, Long> offsets = (Map<TopicPartition, Long>) executionContext.get(TOPIC_PARTITION_OFFSETS);
      for (Map.Entry<TopicPartition, Long> entry : offsets.entrySet()) {
        this.partitionOffsets.put(entry.getKey(), entry.getValue() == 0 ? 0 : entry.getValue() + 1);
      }
    }
    this.kafkaConsumer.assign(this.topicPartitions);
    this.partitionOffsets.forEach(this.kafkaConsumer::seek);
  }

  @Nullable
  @Override
  public V read() {
    if (this.consumerRecords == null || !this.consumerRecords.hasNext()) {
      this.consumerRecords = this.kafkaConsumer.poll(this.pollTimeout).iterator();
    }
    if (this.consumerRecords.hasNext()) {
      ConsumerRecord<K, V> record = this.consumerRecords.next();
      this.partitionOffsets.put(new TopicPartition(record.topic(), record.partition()), record.offset());
      return record.value();
    }
    else {
      return null;
    }
  }

  @Override
  public void update(ExecutionContext executionContext) {
    if (this.saveState) {
      executionContext.put(TOPIC_PARTITION_OFFSETS, new HashMap<>(this.partitionOffsets));
    }
    this.kafkaConsumer.commitSync();
  }

  @Override
  public void close() {
    if (this.kafkaConsumer != null) {
      this.kafkaConsumer.close();
    }
  }
}
