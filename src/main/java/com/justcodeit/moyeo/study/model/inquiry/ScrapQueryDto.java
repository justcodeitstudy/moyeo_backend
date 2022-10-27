package com.justcodeit.moyeo.study.model.inquiry;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "scrapQueryDto", description = "북마크 리스트 조회용 dto")
@Getter
@NoArgsConstructor
public class ScrapQueryDto {

  @Schema(description = "북마크 id 값")
  private Long id;

  @Schema(description = "모집글 id 값")
  private Long postId;

  @Schema(description = "모집글 제목")
  private String title;

  @Schema(description = "모집글 생성일")
  private LocalDateTime createdAt;

  @Schema(description = "모집글 조회수")
  private Long viewCount;

  @Schema(description = "모집글 스킬 조회 dto 리스트")
  private List<PostSkillQueryDto> postSkills;

  @QueryProjection
  public ScrapQueryDto(Long id, Long postId, String title, LocalDateTime createdAt, Long viewCount) {
    this.id = id;
    this.postId = postId;
    this.title = title;
    this.createdAt = createdAt;
    this.viewCount = viewCount;
  }

  public void setPostSkills(List<PostSkillQueryDto> postSkills) {
    this.postSkills = postSkills;
  }
}