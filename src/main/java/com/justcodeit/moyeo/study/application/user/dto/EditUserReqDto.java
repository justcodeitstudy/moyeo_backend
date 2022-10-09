package com.justcodeit.moyeo.study.application.user.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
public class EditUserReqDto {
    @NotBlank
    private String nickname;
    private String introduction;
    @NotEmpty
    private List<Long> skillIds;
}
