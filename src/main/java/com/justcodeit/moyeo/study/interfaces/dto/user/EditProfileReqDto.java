package com.justcodeit.moyeo.study.interfaces.dto.user;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EditProfileReqDto {

    @NotBlank
    private String nickname;
    private String introduction;
    @NotEmpty
    private List<Long> skillIds;
}