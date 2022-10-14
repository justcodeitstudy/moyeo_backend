package com.justcodeit.moyeo.study.application.post;

import com.justcodeit.moyeo.study.application.skill.exception.SkillCannotFoundException;
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
import org.springframework.transaction.annotation.Isolation;
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
        PostMapper postMapper = PostMapper.POST_MAPPER;
        Post post = postMapper.createReqDtoToEntity(postCreateReqDto);
        postRepository.save(post);

        postCreateReqDto.getSkillIdList()
            .stream()
            .forEach(
                skillNumber -> postSkillRepository.save(
                    new PostSkill(post, skillRepository.findById(skillNumber)
                        .orElseThrow(SkillCannotFoundException::new))
                )
            );
        post.getRecruitmentList()
            .stream()
            .map(recruitment -> recruitment.setPost(post))
            .collect(Collectors.toList());
        recruitmentRepository.saveAll(post.getRecruitmentList());

        return post.getId();
    }

    @Transactional(readOnly = true)
    public PostResDto findPost(Long id) {
        Post post = postCustomRepository.findById(id);
        return PostMapper.POST_MAPPER.entityToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResDto> findPostAll(Pageable pageable) {
        List<Post> postList = postCustomRepository.findAll(pageable);
        return PostMapper.POST_MAPPER.entityListToDtoList(postList);
    }
}