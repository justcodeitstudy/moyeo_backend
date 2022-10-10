package com.justcodeit.moyeo.study.interfaces.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor
public class EditUserReqDto {
    @NotBlank
    private String nickname;
    private String introduction;
    @NotEmpty
    private List<Long> skillIds;
}
