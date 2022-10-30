package com.justcodeit.moyeo.study.interfaces.dto.scrap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "postSkillQueryDto", description = "모집글 하나의 스킬 이름 리스트 조회용 dto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostSkillResponseDto {
  @JsonIgnore
  @Schema(description = "PostSkill 테이블 id 값", hidden = true)
  private Long id;

  @JsonIgnore
  @Schema(description = "모집글 id 값")
  private Long postId;

  @JsonProperty(value = "id", index = 1)
  @Schema(description = "스킬 id 값")
  private Long skillId;

  @Schema(description = "스킬 이름")
  private String name;
}