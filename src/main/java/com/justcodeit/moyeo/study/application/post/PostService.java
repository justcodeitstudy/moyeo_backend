package com.justcodeit.moyeo.study.application.post;

import com.justcodeit.moyeo.study.application.skill.exception.SkillCannotFoundException;
import com.justcodeit.moyeo.study.interfaces.dto.post.CardResDto;
import com.justcodeit.moyeo.study.interfaces.dto.post.PostCreateReqDto;
import com.justcodeit.moyeo.study.interfaces.dto.post.PostResDto;
import com.justcodeit.moyeo.study.interfaces.dto.post.PostSearchCondition;
import com.justcodeit.moyeo.study.interfaces.dto.post.RecruitmentStatusReqDto;
import com.justcodeit.moyeo.study.interfaces.mapper.PostMapper;
import com.justcodeit.moyeo.study.model.post.PostStatus;
import com.justcodeit.moyeo.study.persistence.Post;
import com.justcodeit.moyeo.study.persistence.PostSkill;
import com.justcodeit.moyeo.study.persistence.repository.PostRepository;
import com.justcodeit.moyeo.study.persistence.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
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
        Post post = postCustomRepository.findByIdCustom(id);
        return PostMapper.INSTANCE.entityToDto(post);
    }

    @Transactional(readOnly = true)
    public List<CardResDto> findPostAll(Pageable pageable, String userId, PostSearchCondition postSearchReqDto) {
        List<Post> findAllBySearchCondition = postRepository.findAllBySearchCondition(pageable, postSearchReqDto);
        return PostMapper.INSTANCE.entityListToCardResDtoList(findAllBySearchCondition);
    }

    @Transactional
    public void postRecruitStatusChange(Long postId, String userId, RecruitmentStatusReqDto recruitmentStatusReqDto) {
        isWriter(postId, userId);
        Post post = postRepository.findByIdCustom(postId);
        post.changeRecruitStatus(recruitmentStatusReqDto.getStatus());
    }

    private void isWriter(Long postId, String userId) {
        boolean isUserPostExist = postRepository.existByIdAndUserIdAndPostStatusNormal(postId, userId, PostStatus.NORMAL);
        if(!isUserPostExist) {
            throw new AccessDeniedException("User is Not Writer");
        }
    }
}