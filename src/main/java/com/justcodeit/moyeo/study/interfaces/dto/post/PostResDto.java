package com.justcodeit.moyeo.study.interfaces.dto.post;

import com.justcodeit.moyeo.study.interfaces.dto.postskill.PostSkillResDto;
import com.justcodeit.moyeo.study.interfaces.dto.recruitment.RecruitmentDto;
import com.justcodeit.moyeo.study.model.post.RecruitStatus;
import com.justcodeit.moyeo.study.model.post.PostType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PostResDto {
    private Long id;
    private String title;
    private String content;
    private PostType postType;
    private RecruitStatus recruitStatus;
    private List<RecruitmentDto> recruitmentList;
    private List<PostSkillResDto> postSkills;
}
