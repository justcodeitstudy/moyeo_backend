package com.justcodeit.moyeo.study.application.post;

import com.justcodeit.moyeo.study.application.post.exception.PostCannotFoundException;
import com.justcodeit.moyeo.study.application.skill.exception.SkillCannotFoundException;
import com.justcodeit.moyeo.study.interfaces.dto.post.PostCreateReqDto;
import com.justcodeit.moyeo.study.interfaces.dto.post.PostResDto;
import com.justcodeit.moyeo.study.interfaces.dto.post.PostSearchCondition;
import com.justcodeit.moyeo.study.interfaces.dto.post.PostSimpleResponseDto;
import com.justcodeit.moyeo.study.interfaces.dto.post.RecruitmentStatusReqDto;
import com.justcodeit.moyeo.study.interfaces.mapper.PostMapper;
import com.justcodeit.moyeo.study.model.inquiry.PostQueryDto;
import com.justcodeit.moyeo.study.model.post.PostStatus;
import com.justcodeit.moyeo.study.persistence.Post;
import com.justcodeit.moyeo.study.persistence.PostSkill;
import com.justcodeit.moyeo.study.persistence.repository.PostRepository;
import com.justcodeit.moyeo.study.persistence.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final SkillRepository skillRepository;
    @Transactional
    public Long createPost(PostCreateReqDto postCreateReqDto, String userId) {
        PostMapper postMapper = PostMapper.INSTANCE;
        postCreateReqDto.setUserId(userId);

        Post post = postMapper.createReqDtoToEntity(postCreateReqDto);
        post.setRecruitmentList(postCreateReqDto.getRecruitmentList());
        List<PostSkill> postSkills = postCreateReqDto.getSkillIdList()
                                        .stream()
                                        .map( skillId ->
                                                new PostSkill(
                                                    post,
                                                    skillRepository.findById(skillId).orElseThrow(SkillCannotFoundException::new)
                                                )
                                        )
                                        .collect(Collectors.toList());
        post.setPostSkills(postSkills);
        postRepository.save(post);

        return post.getId();
    }

    @Transactional(readOnly = true)
    public PostResDto findPost(Long id) {
        Post post = postRepository.findByIdCustom(id).orElseThrow(PostCannotFoundException::new);
        return PostMapper.INSTANCE.entityToDto(post);
    }

    @Transactional(readOnly = true)
    public Page<PostQueryDto> findPostAll(Pageable pageable, String userId, PostSearchCondition postSearchReqDto) {
        return postRepository.findPostList(userId, postSearchReqDto, pageable);
    }

    @Transactional
    public void postRecruitStatusChange(Long postId, String userId, RecruitmentStatusReqDto recruitmentStatusReqDto) {
        isWriter(postId, userId);
        Post post = postRepository.findByIdCustom(postId).orElseThrow(PostCannotFoundException::new);
        post.changeRecruitStatus(recruitmentStatusReqDto.getStatus());
    }

    @Transactional
    public void postDelete(Long postId, String userId) {
        isWriter(postId, userId);
        Post post = postRepository.findByIdCustom(postId).orElseThrow(PostCannotFoundException::new);
        post.delete();
    }

    private void isWriter(Long postId, String userId) {
        boolean isUserPostExist = postRepository.existByIdAndUserIdAndPostStatusNormal(postId, userId, PostStatus.NORMAL);
        if(!isUserPostExist) {
            throw new AccessDeniedException("User is Not Writer");
        }
    }

    @Transactional(readOnly = true)
    public List<PostSimpleResponseDto> findPostListByUser(String userId) {
        return postRepository.findPostListByUserId(userId).stream()
                .map(dto -> new PostSimpleResponseDto(dto.getPostId(), dto.getTitle(), dto.getCreatedAt(), dto.getViewCount(), dto.getIsScrapped(), dto.getSkillList()))
                .collect(Collectors.toList());
    }
}