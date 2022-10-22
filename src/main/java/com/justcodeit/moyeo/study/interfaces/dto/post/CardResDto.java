package com.justcodeit.moyeo.study.interfaces.dto.post;

import com.justcodeit.moyeo.study.interfaces.dto.recruitment.RecruitmentDto;
import com.justcodeit.moyeo.study.model.post.PostType;
import com.justcodeit.moyeo.study.model.post.RecruitStatus;
import com.justcodeit.moyeo.study.model.skill.CardSkillDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Post List API 호출시 사용되는 Dto
 */
@Setter
@Getter
public class CardResDto {
    private Long id;
    private String title;
    private String content;
    private PostType postType;
    private RecruitStatus recruitStatus;
    private List<RecruitmentDto> recruitmentList;
    private Long viewCount;
    private List<CardSkillDto> postSkills;
    // 북마크 여부 추후 추가 필요
    private boolean isScrap;
    private LocalDateTime createDate;
}
