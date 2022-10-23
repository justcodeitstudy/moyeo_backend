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
  private Boolean isScrapped;
  private List<PostSkillQueryDto> postSkills;

  @QueryProjection
  public PostQueryDto(Long postId, String title, LocalDateTime createDate, Long viewCount, Boolean isScrapped) {
    this.postId = postId;
    this.title = title;
    this.createDate = createDate;
    this.viewCount = viewCount;
    this.isScrapped = isScrapped;
  }

  public void setPostSkills(List<PostSkillQueryDto> postSkills) {
    this.postSkills = postSkills;
  }
}