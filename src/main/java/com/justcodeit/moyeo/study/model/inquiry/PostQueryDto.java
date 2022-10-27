package com.justcodeit.moyeo.study.model.inquiry;

import com.justcodeit.moyeo.study.interfaces.dto.scrap.PostSkillResponseDto;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "postQueryDto", description = "모집글 리스트 조회용 dto")
@Getter
@NoArgsConstructor
public class PostQueryDto {

  @Schema(description = "모집글 id 값")
  private Long postId;

  @Schema(description = "모집글 제목")
  private String title;

  @Schema(description = "모집글 생성일")
  private LocalDateTime createdAt;

  @Schema(description = "모집글 조회수")
  private Long viewCount;

  @Schema(description = "모집글 북마크 여부")
  private Boolean isScrapped;

  @Schema(description = "모집글 스킬 조회 dto 리스트")
  private List<PostSkillResponseDto> postSkills;

  @QueryProjection
  public PostQueryDto(Long postId, String title, LocalDateTime createdAt, Long viewCount, Boolean isScrapped) {
    this.postId = postId;
    this.title = title;
    this.createdAt = createdAt;
    this.viewCount = viewCount;
    this.isScrapped = isScrapped;
  }

  public void setPostSkills(List<PostSkillResponseDto> postSkills) {
    this.postSkills = postSkills;
  }
}