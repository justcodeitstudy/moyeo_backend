package com.justcodeit.moyeo.study.interfaces.dto.scrap;

import com.justcodeit.moyeo.study.persistence.Scrap;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ScrapResponseDto {

  private Long id;
  private String userId;
  private Long postId;
  private LocalDateTime createdAt;

  public ScrapResponseDto(Scrap scrap) {
    id = scrap.getId();
    userId = scrap.getUserId();
    postId = scrap.getPostId();
    createdAt = scrap.getCreatedAt();
  }
}