package com.justcodeit.moyeo.study.interfaces.mapper;

import com.justcodeit.moyeo.study.interfaces.dto.skill.SkillCreateDto;
import com.justcodeit.moyeo.study.model.skill.SkillDto;
import com.justcodeit.moyeo.study.persistence.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SkillMapper {
    SkillMapper SKILL_INSTANCE = Mappers.getMapper(SkillMapper.class);

    SkillDto entityToSkillDto(Skill skill);
    Skill skillDtoToEntity(SkillDto skillDto);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    SkillDto skillDtoToEntity(SkillCreateDto skillCreateDto);

    List<SkillDto> entityListToSkillList(List<Skill> skill);
}
