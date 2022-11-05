package com.justcodeit.moyeo.study.interfaces.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.justcodeit.moyeo.study.interfaces.dto.postskill.PostSkillResDto;
import com.justcodeit.moyeo.study.interfaces.dto.recruitment.RecruitmentDto;
import com.justcodeit.moyeo.study.model.post.RecruitStatus;
import com.justcodeit.moyeo.study.model.post.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "postResDto", description = "모집글 단건 조회시 반환하는 정보 객체")
@Setter
@Getter
public class PostResDto {
    @Schema(description = "모집글 번호")
    private Long id;
    @Schema(description = "모집글 번호")
    private String title;
    @Schema(description = "모집글 번호")
    private String content;
    private PostType postType;
    private RecruitStatus recruitStatus;
    private List<RecruitmentDto> recruitmentList;
    @JsonProperty(value = "skillList")
    private List<PostSkillResDto> postSkills;
    private LocalDateTime createdAt;
}
