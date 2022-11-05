package com.justcodeit.moyeo.study.interfaces.mapper;

import com.justcodeit.moyeo.study.interfaces.dto.post.PostCreateReqDto;
import com.justcodeit.moyeo.study.interfaces.dto.post.PostResDto;
import com.justcodeit.moyeo.study.persistence.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {RecruitmentMapper.class, PostSkillMapper.class})
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "postSkills", ignore = true)
    @Mapping(target = "recruitmentList", ignore = true)
    @Mapping(target = "recruitStatus", ignore = true)
    @Mapping(target = "postStatus", ignore = true)
    Post createReqDtoToEntity(PostCreateReqDto postCreateReqDto);

    @Mapping(target = "skillIds", ignore = true)
    PostResDto entityToDto(Post post);

    List<PostResDto> entityListToDtoList(List<Post> postList);
}
