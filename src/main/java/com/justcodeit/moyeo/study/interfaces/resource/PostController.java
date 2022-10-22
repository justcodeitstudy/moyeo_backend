package com.justcodeit.moyeo.study.interfaces.resource;

import com.justcodeit.moyeo.study.application.post.PostService;
import com.justcodeit.moyeo.study.interfaces.dto.post.CardResDto;
import com.justcodeit.moyeo.study.interfaces.dto.post.PostCreateReqDto;
import com.justcodeit.moyeo.study.interfaces.dto.post.PostResDto;
import com.justcodeit.moyeo.study.model.jwt.UserToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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
    @PostMapping
    public PostResDto createPost(@RequestBody @Valid PostCreateReqDto postCreateRequestDto) {
        Long postId = postService.createPost(postCreateRequestDto);
        return postService.findPost(postId);
    }

    @GetMapping("/{id}")
    public PostResDto findPostById(@PathVariable Long id) {
        return postService.findPost(id);
    }

    @GetMapping
    public List<CardResDto> findPostAll(Pageable pageable, @AuthenticationPrincipal UserToken userToken) {
        String userId = "";
        if(userToken != null) {
            userId = userToken.getUserId();
        }
        return postService.findPostAll(pageable, userId);
    }
}