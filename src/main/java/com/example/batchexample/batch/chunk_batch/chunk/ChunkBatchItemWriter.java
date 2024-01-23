package com.example.batchexample.batch.chunk_batch.chunk;

import com.example.batchexample.domain.Post;
import com.example.batchexample.domain.PostRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChunkBatchItemWriter implements ItemWriter<List<Post>> {
  private final PostRepository postRepository;

  public ChunkBatchItemWriter(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Override
  public void write(List<? extends List<Post>> items) throws Exception {
    for (List<Post> item : items) {
      postRepository.saveAll(item);
    }
  }
}
