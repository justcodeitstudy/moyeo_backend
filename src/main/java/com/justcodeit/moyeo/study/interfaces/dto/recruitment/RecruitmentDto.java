package com.justcodeit.moyeo.study.interfaces.dto.recruitment;

import com.justcodeit.moyeo.study.model.post.RecruitType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class RecruitmentDto {
    private Long id;
    private RecruitType recruitType;
    @Min(value = 2)
    @Max(value = 10)
    private int recruitPeopleNum;
}
