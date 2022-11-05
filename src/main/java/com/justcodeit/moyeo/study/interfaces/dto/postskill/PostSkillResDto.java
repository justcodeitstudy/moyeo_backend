package com.justcodeit.moyeo.study.interfaces.dto.postskill;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "모집글의 기술스택 반환 객체.")
@Getter
@Setter
public class PostSkillResDto {
    @Schema(description = "모집글과 기술스택의 중간 테이블의 고유값. 기술스택 수정시 필요")
    private Long id;
    @Schema(description = "기술스택의 고유값.")
    private Long skillId;
}