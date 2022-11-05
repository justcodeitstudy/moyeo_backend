package com.justcodeit.moyeo.study.interfaces.mapper;

import com.justcodeit.moyeo.study.interfaces.dto.postskill.PostSkillResDto;
import com.justcodeit.moyeo.study.model.skill.CardSkillDto;
import com.justcodeit.moyeo.study.persistence.PostSkill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostSkillMapper {
    PostSkillMapper INSTANCE = Mappers.getMapper(PostSkillMapper.class);

    @Mapping(target = "id", source = "skill.id")
    @Mapping(target = "name", ignore = true)
    PostSkillResDto entityToDto(PostSkill postSkill);
    @Mapping(target = "id", source = "postSkill.skill.id")
    @Mapping(target = "name", source = "postSkill.skill.name")
    CardSkillDto entityToCardResDto(PostSkill postSkill);
}
