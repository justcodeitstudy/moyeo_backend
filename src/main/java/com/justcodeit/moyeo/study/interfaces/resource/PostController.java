package com.justcodeit.moyeo.study.interfaces.resource;

import com.justcodeit.moyeo.study.interfaces.dto.post.PostCreateRequestDto;
import com.justcodeit.moyeo.study.persistence.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.justcodeit.moyeo.study.interfaces.mapper.PostMapper.POST_INSTANCE;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {

    @PostMapping
    public Post createPost(@RequestBody PostCreateRequestDto postCreateRequestDto) {
        Post post = POST_INSTANCE.createDtoToPost(postCreateRequestDto);
        log.info(post.toString());

        return null;
    }
}
