package com.justcodeit.moyeo.study.model.skill;

import com.justcodeit.moyeo.study.model.type.SkillCategory;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SkillDto {
    private Long id;
    private SkillCategory category;
    private String name;
    private String imageUrl;
    private Integer orderNum;
}