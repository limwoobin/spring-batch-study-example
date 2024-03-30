package com.example.batchexample.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "posts")
@Getter
@Setter
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
    this.title += title;
  }

  @Override
  public String toString() {
    return "Post{" +
      "id=" + id +
      ", title='" + title + '\'' +
      ", author='" + author + '\'' +
      '}';
  }
}
