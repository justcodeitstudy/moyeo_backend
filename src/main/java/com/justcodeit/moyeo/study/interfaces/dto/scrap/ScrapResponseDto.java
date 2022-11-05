package com.justcodeit.moyeo.study.interfaces.dto.scrap;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "scrapQueryDto", description = "북마크 리스트 조회용 dto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScrapResponseDto {

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

  @JsonProperty(value = "skillList")
  @Schema(description = "모집글 스킬 조회 dto 리스트")
  private List<PostSkillResponseDto> postSkills;
}