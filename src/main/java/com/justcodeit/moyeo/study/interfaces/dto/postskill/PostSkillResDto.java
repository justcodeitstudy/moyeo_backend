package com.justcodeit.moyeo.study.interfaces.dto.postskill;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "모집글의 기술스택 반환 객체.")
@Getter
@Setter
public class PostSkillResDto {
    @Schema(description = "기술스택의 고유 Id.")
    private Long id;
    @Schema(description = "기술스택의 고유값.")
    private String name;
}