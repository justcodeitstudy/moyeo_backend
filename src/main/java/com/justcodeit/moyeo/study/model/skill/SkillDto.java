package com.justcodeit.moyeo.study.model.skill;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.justcodeit.moyeo.study.model.type.SkillCategory;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SkillDto {
    private Long id;
    private SkillCategory skillCategory;
    private String name;
    @JsonProperty(value = "")
    private String imageUrl;
    private String folderName;
    private Integer orderNum;
}