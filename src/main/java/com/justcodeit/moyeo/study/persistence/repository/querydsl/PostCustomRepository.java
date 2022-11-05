package com.justcodeit.moyeo.study.persistence.repository.querydsl;

import com.justcodeit.moyeo.study.interfaces.dto.post.PostSearchCondition;
import com.justcodeit.moyeo.study.model.inquiry.PostQueryDto;
import com.justcodeit.moyeo.study.model.post.PostStatus;
import com.justcodeit.moyeo.study.persistence.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostCustomRepository {
    Optional<Post> findByIdCustom(Long id);
    List<Post> findAllBySearchCondition(Pageable pageable, PostSearchCondition searchCondition);
    boolean existByIdAndUserIdAndPostStatusNormal(Long id, String userId, PostStatus postStatus);
    Page<PostQueryDto> findPostList(String userId, PostSearchCondition searchCondition, Pageable pageable);
    List<PostQueryDto> findPostListByUserId(String userId);
}