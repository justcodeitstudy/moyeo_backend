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

    @Mapping(target = "skillId", source = "skill.id")
    PostSkillResDto entityToDto(PostSkill postSkill);
    @Mapping(source = "postSkill.skill.id", target = "id")
    @Mapping(source = "postSkill.skill.name", target = "name")
    CardSkillDto entityToCardResDto(PostSkill postSkill);
}
