package com.justcodeit.moyeo.study.persistence.repository.querydsl;

import com.justcodeit.moyeo.study.model.inquiry.PostQueryDto;
import com.justcodeit.moyeo.study.persistence.Post;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostCustomRepository {
    Post findById(Long id);
    List<Post> findAll(Pageable pageable);
    List<PostQueryDto> findPostListByUserId(String userId);
}