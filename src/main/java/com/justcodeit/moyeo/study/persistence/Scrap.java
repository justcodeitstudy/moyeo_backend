package com.justcodeit.moyeo.study.persistence;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Scrap {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String userId;

  private Long postId;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt;

  public Scrap(String userId, Long postId) {
    this.userId = userId;
    this.postId = postId;
  }

  public Long getId() {
    return id;
  }

  public String getUserId() {
    return userId;
  }

  public Long getPostId() {
    return postId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}