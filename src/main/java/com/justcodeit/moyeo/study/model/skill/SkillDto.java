package com.justcodeit.moyeo.study.model.skill;

import com.justcodeit.moyeo.study.model.type.SkillCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springdoc.api.annotations.ParameterObject;

@Schema(description = "기술스택 Request 용 객체")
@Setter
@Getter
public class SkillDto {
    @Schema(description = "기술스택 번호", type = "Long", defaultValue = "NULL")
    private Long id;
    @Schema(description = "해당 Skill이 어떤 분야에 속하는지에 대한 카테고리", type = "Long")
    private SkillCategory category;
    @Schema(description = "이름", type = "String")
    private String name;
    @Schema(description = "s3 주소", type = "String")
    private String imageUrl;
    @Schema(description = "정렬 순서", type = "String")
    private Integer orderNum;
}