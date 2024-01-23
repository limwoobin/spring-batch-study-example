package com.example.batchexample.batch.chunk_batch.chunk;

import com.example.batchexample.domain.Post;
import com.example.batchexample.domain.PostRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ChunkBatchItemProcessor implements ItemProcessor<List<Long>, List<Post>> {
  private final PostRepository postRepository;

  public ChunkBatchItemProcessor(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  @Override
  public List<Post> process(List<Long> items) throws Exception {
    List<Post> posts = new ArrayList<>();
    for (Long item : items) {
      Optional<Post> optionalPost = postRepository.findById(item);
      if (optionalPost.isEmpty()) {
        return null;
      }

      Post post = optionalPost.get();
      post.changeTitle(post.getTitle() + "-");
      posts.add(post);
    }

    return posts;
  }
}
