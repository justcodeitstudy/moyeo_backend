package com.justcodeit.moyeo.study.interfaces.dto.post;

import com.justcodeit.moyeo.study.model.post.PostType;
import com.justcodeit.moyeo.study.model.post.Recruitment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PostCreateRequestDto {
    private PostType postType;
    private String title;
    private String content;

    private List<Recruitment> recruitmentList;
    private String contactInformation;
    private String skillStack;
}
