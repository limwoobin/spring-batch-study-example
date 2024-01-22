package com.example.batchexample.batch.example;

import com.example.batchexample.domain.Post;
import com.example.batchexample.domain.PostRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChunkExampleItemWriter implements ItemWriter<Post> {

  private final PostRepository postRepository;

  public ChunkExampleItemWriter(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Override
  public void write(List<? extends Post> items) throws Exception {
    postRepository.saveAll(items);
  }
}
