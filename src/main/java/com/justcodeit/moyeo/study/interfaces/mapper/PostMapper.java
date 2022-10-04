package com.justcodeit.moyeo.study.interfaces.mapper;

import com.justcodeit.moyeo.study.interfaces.dto.post.PostCreateRequestDto;
import com.justcodeit.moyeo.study.persistence.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper POST_INSTANCE = Mappers.getMapper(PostMapper.class);

    Post createDtoToPost(PostCreateRequestDto postCreateRequestDto);
}
