package com.justcodeit.moyeo.study.persistence;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * querydsl 테스트용 엔티티입니다.
 * 실제로 사용하지 않을 가능성이 높습니다.
 */

@ToString
@Setter
@Getter
@Entity
@Table(name = "posts")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String content;

  @OneToOne
  @JoinColumn(name = "id")
  private User user;

  public Post(String title, String content, User user) {
    this.title = title;
    this.content = content;
    this.user = user;
  }

  public Post() {

  }

  public Post update(String title, String content) {
    this.title = title;
    this.content = content;
    return this;
  }

}
