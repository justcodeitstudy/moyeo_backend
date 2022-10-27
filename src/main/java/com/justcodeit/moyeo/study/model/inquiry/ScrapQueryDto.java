package com.justcodeit.moyeo.study.model.inquiry;

import com.justcodeit.moyeo.study.interfaces.dto.scrap.PostSkillResponseDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ScrapQueryDto {

  private Long id;
  private Long postId;
  private String title;
  private LocalDateTime createdAt;
  private Long viewCount;
  private List<PostSkillResponseDto> postSkills;

  @QueryProjection
  public ScrapQueryDto(Long id, Long postId, String title, LocalDateTime createdAt, Long viewCount) {
    this.id = id;
    this.postId = postId;
    this.title = title;
    this.createdAt = createdAt;
    this.viewCount = viewCount;
  }

  public void setPostSkills(List<PostSkillResponseDto> postSkills) {
    this.postSkills = postSkills;
  }
}