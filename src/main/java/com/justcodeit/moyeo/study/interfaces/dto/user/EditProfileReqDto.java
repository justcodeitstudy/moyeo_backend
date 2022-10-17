package com.justcodeit.moyeo.study.interfaces.dto.user;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditProfileReqDto {

    @NotBlank
    @Pattern(regexp = "[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|\\s]{2,10}")
    private String nickname;
    @Size(max = 3000)
    private String introduction;
    @NotEmpty
    private List<Long> skillIds;
}