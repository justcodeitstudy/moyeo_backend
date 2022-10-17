package com.justcodeit.moyeo.study.application.post;

import com.justcodeit.moyeo.study.application.skill.exception.SkillCannotFoundException;
import com.justcodeit.moyeo.study.interfaces.dto.post.CardResDto;
import com.justcodeit.moyeo.study.interfaces.dto.post.PostCreateReqDto;
import com.justcodeit.moyeo.study.interfaces.dto.post.PostResDto;
import com.justcodeit.moyeo.study.interfaces.mapper.PostMapper;
import com.justcodeit.moyeo.study.persistence.Post;
import com.justcodeit.moyeo.study.persistence.PostSkill;
import com.justcodeit.moyeo.study.persistence.repository.PostRepository;
import com.justcodeit.moyeo.study.persistence.repository.PostSkillRepository;
import com.justcodeit.moyeo.study.persistence.repository.RecruitmentRepository;
import com.justcodeit.moyeo.study.persistence.repository.SkillRepository;
import com.justcodeit.moyeo.study.persistence.repository.querydsl.PostCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostSkillRepository postSkillRepository;
    private final SkillRepository skillRepository;
    private final PostCustomRepository postCustomRepository;
    private final RecruitmentRepository recruitmentRepository;

    @Transactional
    public Long createPost(PostCreateReqDto postCreateReqDto) {
        PostMapper postMapper = PostMapper.INSTANCE;
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
        Post post = postCustomRepository.findById(id);
        return PostMapper.INSTANCE.entityToDto(post);
    }

    @Transactional(readOnly = true)
    public List<CardResDto> findPostAll(Pageable pageable, String userId) {
        List<Post> postList = postCustomRepository.findAll(pageable);
        return PostMapper.INSTANCE.entityToCardResDto(postList);
    }
}