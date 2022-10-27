package com.justcodeit.moyeo.study.model.inquiry;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "postSkillQueryDto", description = "모집글 하나의 스킬 이름 리스트 조회용 dto")
@Getter
@NoArgsConstructor
public class PostSkillQueryDto {

  @Schema(description = "PostSkill 테이블 id 값")
  private Long id;

  @Schema(description = "모집글 id 값")
  private Long postId;

  @Schema(description = "스킬 id 값")
  private Long skillId;

  @Schema(description = "스킬 이름")
  private String name;

  @QueryProjection
  public PostSkillQueryDto(Long id, Long postId, Long skillId, String name) {
    this.id = id;
    this.postId = postId;
    this.skillId = skillId;
    this.name = name;
  }
}