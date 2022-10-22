package com.justcodeit.moyeo.study.interfaces.dto.scrap;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScrapQueryDto {

  // scrap
  private Long id;

  // TODO : 작성일, 조회수, 기술 스택 추가 예정
  // post
  private Long postId;
  private String title;


  @QueryProjection
  public ScrapQueryDto(Long id, Long postId, String title) {
    this.id = id;
    this.postId = postId;
    this.title = title;
  }
}