package com.justcodeit.moyeo.study.interfaces.dto.post;

import com.justcodeit.moyeo.study.interfaces.dto.scrap.PostSkillResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "postQueryDto", description = "모집글 리스트 조회용 dto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostSimpleResponseDto {

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
}