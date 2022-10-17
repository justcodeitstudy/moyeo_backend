package com.justcodeit.moyeo.study.interfaces.dto.post;

import com.justcodeit.moyeo.study.interfaces.dto.recruitment.RecruitmentDto;
import com.justcodeit.moyeo.study.model.post.PostStatus;
import com.justcodeit.moyeo.study.model.post.RecruitStatus;
import com.justcodeit.moyeo.study.model.post.PostType;
import com.justcodeit.moyeo.study.model.post.ProgressType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
public class PostCreateReqDto {
    @NotBlank
    private String title;
    @NotBlank
    @Length(min = 10,max = 5000)
    private String content;
    private PostType postType;
    private ProgressType progressType;
    private RecruitStatus recruitStatus;
    @Valid
    @Size(min = 1, max = 7)
    private List<RecruitmentDto> recruitmentList;
    @Valid
    @Size(min = 1, max = 10)
    private List<Long> skillIdList;
    private String contactInfo;
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    PostCreateReqDto() {
        this.recruitStatus = RecruitStatus.RECRUITING;
        this.postStatus = PostStatus.NORMAL;
    }
}
