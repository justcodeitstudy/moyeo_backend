package com.justcodeit.moyeo.study.interfaces.dto.post;

import com.justcodeit.moyeo.study.model.post.RecruitStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class RecruitmentStatusReqDto {
    @NotNull
    RecruitStatus status;
}
