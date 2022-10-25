package com.justcodeit.moyeo.study.interfaces.dto.post;

import com.justcodeit.moyeo.study.model.post.RecruitStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Schema(name = "recruitmentStatusReqDto", description = "모집글 상태 변경 Request 객체")
@Setter
@Getter
public class RecruitmentStatusReqDto {
    @Schema(description = "모집상태", defaultValue = "RECRUITING")
    @NotNull
    RecruitStatus status;
}
