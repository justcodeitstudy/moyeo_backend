package com.justcodeit.moyeo.study.interfaces.dto.recruitment;

import com.justcodeit.moyeo.study.model.post.RecruitType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Schema(name = "recruitmentDto", description = "모집분야")
@Getter
@Setter
public class RecruitmentDto {
    private Long id;
    @Schema(description = "모집분야 구분", defaultValue = "BACK_END", required = true)
    private RecruitType recruitType;

    @Schema(description = "모집 인원", required = true)
    @Min(value = 2)
    @Max(value = 10)
    private int recruitPeopleNum;
}
