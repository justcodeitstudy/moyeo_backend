package com.justcodeit.moyeo.study.interfaces.resource;

import com.justcodeit.moyeo.study.application.post.PostService;
import com.justcodeit.moyeo.study.interfaces.dto.post.CardResDto;
import com.justcodeit.moyeo.study.interfaces.dto.post.PostCreateReqDto;
import com.justcodeit.moyeo.study.interfaces.dto.post.PostResDto;
import com.justcodeit.moyeo.study.interfaces.dto.post.PostSearchCondition;
import com.justcodeit.moyeo.study.interfaces.dto.post.RecruitmentStatusReqDto;
import com.justcodeit.moyeo.study.model.jwt.UserToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public PostResDto createPost(@RequestBody @Valid PostCreateReqDto postCreateRequestDto, @AuthenticationPrincipal UserToken userToken) {
        Long postId = postService.createPost(postCreateRequestDto, userToken.getUserId());
        return postService.findPost(postId);
    }

    @GetMapping("/{id}")
    public PostResDto findPostById(@PathVariable Long id) {
        return postService.findPost(id);
    }

    @GetMapping
    public List<CardResDto> findPostAll(Pageable pageable, @AuthenticationPrincipal UserToken userToken, @ModelAttribute PostSearchCondition searchCondition) {
        String userId = "";
        if(userToken != null) {
            userId = userToken.getUserId();
        }
        return postService.findPostAll(pageable, userId, searchCondition);
    }
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void postRecruitStatusChange(@PathVariable(name = "id") Long postId, @RequestBody RecruitmentStatusReqDto recruitmentStatusReqDto, @AuthenticationPrincipal UserToken userToken) {
        postService.postRecruitStatusChange(postId, userToken.getUserId(), recruitmentStatusReqDto);
    }
}