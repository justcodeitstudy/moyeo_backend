package com.justcodeit.moyeo.study.interfaces.mapper;

import com.justcodeit.moyeo.study.interfaces.dto.skill.SkillCreateRequestDto;
import com.justcodeit.moyeo.study.model.skill.SkillDto;
import com.justcodeit.moyeo.study.persistence.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SkillMapper {
    SkillMapper SKILL_INSTANCE = Mappers.getMapper(SkillMapper.class);

    Skill skillCreateDtoToEntity(SkillCreateRequestDto skillCreateRequestDto);
    SkillDto entityToSkillDto(Skill skill);

    List<SkillDto> entityListToSkillList(List<Skill> skill);
}
