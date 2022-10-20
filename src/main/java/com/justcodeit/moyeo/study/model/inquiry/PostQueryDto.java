package com.justcodeit.moyeo.study.model.inquiry;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostQueryDto {

  private Long postId;
  private String title;
  private LocalDateTime createDate;
  private Long viewCount;
  private List<PostSkillQueryDto> postSkills;

  @QueryProjection
  public PostQueryDto(Long postId, String title, LocalDateTime createDate, Long viewCount) {
    this.postId = postId;
    this.title = title;
    this.createDate = createDate;
    this.viewCount = viewCount;
  }

  public void setPostSkills(List<PostSkillQueryDto> postSkills) {
    this.postSkills = postSkills;
  }
}