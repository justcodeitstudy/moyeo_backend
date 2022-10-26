package com.justcodeit.moyeo.study.interfaces.dto.post;

import com.justcodeit.moyeo.study.interfaces.dto.recruitment.RecruitmentDto;
import com.justcodeit.moyeo.study.model.post.PostType;
import com.justcodeit.moyeo.study.model.post.ProgressType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.List;

@Schema(name = "postCreateReqDto", description = "모집글 작성 Request Dto")
@Setter
@Getter
public class PostCreateReqDto {
    @Schema(description = "글 제목")
    @NotBlank
    private String title;

    @Schema(description = "글 내용", required = true)
    @NotBlank
    @Length(min = 10,max = 5000)
    private String content;

    @Schema(description = "모집 글 종류", defaultValue = "STUDY", required = true)
    private PostType postType;

    @Schema(description = "진행 방식", required = true)
    private ProgressType progressType;

    @Schema(description = "JWT 토큰의 사용자 값. 보내지 않음.", required = false)
    @Null
    private String userId;

    @Schema(description = "연락 방법. 휴대폰번호, 이메일, 카카오톡 아이디 등 ", required = true)
    private String contactInfo;

    @Schema(description = "모집 분야 목록", required = true)
    @Valid
    @Size(min = 1, max = 7)
    private List<RecruitmentDto> recruitmentList;

    @Schema(description = "기술 스택 ID 목록", required = true)
    @Valid
    @Size(min = 1, max = 10)
    private List<Long> skillIdList;
}
