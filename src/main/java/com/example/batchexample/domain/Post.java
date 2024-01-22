package com.example.batchexample.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String title;

  @Column
  private String author;

  private Post(String title, String author) {
    this.title = title;
    this.author = author;
  }

  public static Post of(String title, String author) {
    return new Post(title, author);
  }

  public void changeTitle(String title) {
    this.title = title;
  }
}
