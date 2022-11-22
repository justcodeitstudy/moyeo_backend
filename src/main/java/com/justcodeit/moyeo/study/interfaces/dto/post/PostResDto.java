package com.justcodeit.moyeo.study.interfaces.dto.post;

import com.justcodeit.moyeo.study.interfaces.dto.recruitment.RecruitmentDto;
import com.justcodeit.moyeo.study.model.post.ProgressType;
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
    @Schema(description = "글 번호")
    private Long id;
    @Schema(description = "제목")
    private String title;
    @Schema(description = "내용")
    private String content;
    @Schema(description = "userId")
    private String userId;
    @Schema(description = "닉네임")
    private String userNick;
    @Schema(description = "진행방식")
    private ProgressType progressType;
    @Schema(description = "조회수")
    private long viewCount;
    @Schema(description = "종류")
    private PostType postType;
    @Schema(description = "모집 상태")
    private RecruitStatus recruitStatus; // 모집상태
    @Schema(description = "모집분류 목록")
    private List<RecruitmentDto> recruitmentList;
    @Schema(description = "기술스택 목록")
    private List<Long> skillIds;
    @Schema(description = "생성일")
    private LocalDateTime createdAt;
}
