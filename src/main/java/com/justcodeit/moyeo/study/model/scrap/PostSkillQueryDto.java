package com.justcodeit.moyeo.study.model.scrap;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostSkillQueryDto {

  // postSkill
  private Long id;

  // post
  private Long postId;

  // skill
  private Long skillId;
  private String name;

  @QueryProjection
  public PostSkillQueryDto(Long id, Long postId, Long skillId, String name) {
    this.id = id;
    this.postId = postId;
    this.skillId = skillId;
    this.name = name;
  }
}