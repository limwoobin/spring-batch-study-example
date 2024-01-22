package com.example.batchexample.batch.chunk_single.example;

import com.example.batchexample.domain.Post;
import com.example.batchexample.domain.PostRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ChunkExampleItemProcessor implements ItemProcessor<Long, Post> {

  private final PostRepository postRepository;

  public ChunkExampleItemProcessor(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Override
  public Post process(Long id) throws Exception {
    Optional<Post> optionalPost = postRepository.findById(id);
    if (optionalPost.isEmpty()) {
      return null;
    }

    Post post = optionalPost.get();
    String title = post.getTitle();
    post.changeTitle(title + " changed");
    return post;
  }
}
