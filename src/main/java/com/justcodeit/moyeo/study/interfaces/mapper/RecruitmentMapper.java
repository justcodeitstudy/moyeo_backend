package com.justcodeit.moyeo.study.interfaces.mapper;

import com.justcodeit.moyeo.study.interfaces.dto.recruitment.RecruitmentDto;
import com.justcodeit.moyeo.study.persistence.Recruitment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RecruitmentMapper {
    RecruitmentMapper INSTANCE = Mappers.getMapper(RecruitmentMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "post", ignore = true)
    Recruitment dtoToEntity(RecruitmentDto recruitmentDto);
    RecruitmentDto entityToDto(Recruitment recruitment);
    List<Recruitment> reqDtoListToEntityList(List<RecruitmentDto> recruitmentDto);
}
