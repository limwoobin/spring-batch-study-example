package com.example.batchexample.application;

import com.example.batchexample.domain.Post;
import com.example.batchexample.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  @Transactional
  public void save(String title, String author) {
    Post post = Post.of(title, author);
    postRepository.save(post);
  }
}
