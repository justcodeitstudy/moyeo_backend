package com.justcodeit.moyeo.study.interfaces.dto.post;

import com.justcodeit.moyeo.study.model.post.RecruitStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PostSearchCondition {
    private String title;
    private RecruitStatus recruitStatus;
    private List<Long> skillList;
}
