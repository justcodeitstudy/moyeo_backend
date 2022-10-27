package com.justcodeit.moyeo.study.interfaces.dto.scrap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "postSkillQueryDto", description = "모집글 하나의 스킬 이름 리스트 조회용 dto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostSkillResponseDto {

  @Schema(description = "PostSkill 테이블 id 값")
  private Long id;

  @Schema(description = "모집글 id 값")
  private Long postId;

  @Schema(description = "스킬 id 값")
  private Long skillId;

  @Schema(description = "스킬 이름")
  private String name;
}