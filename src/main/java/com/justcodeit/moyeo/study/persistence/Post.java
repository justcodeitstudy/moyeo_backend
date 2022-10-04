package com.justcodeit.moyeo.study.persistence;

import com.justcodeit.moyeo.study.model.post.PostStatus;
import com.justcodeit.moyeo.study.model.post.PostType;
import com.justcodeit.moyeo.study.model.post.Recruitment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "post")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String content;
  @Enumerated(EnumType.STRING)
  private PostType postType;

  private PostStatus postStatus;
  private String skillStack;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "post_id")
  private List<Recruitment> recruitmentList; // 모집 분야

  @Builder
  public Post(String title, String content, PostType postType, String skillStack, List<Recruitment> recruitmentList) {
    this.title = title;
    this.content = content;
    this.postType = postType;
    this.skillStack = skillStack;
    this.recruitmentList = recruitmentList;
  }
}