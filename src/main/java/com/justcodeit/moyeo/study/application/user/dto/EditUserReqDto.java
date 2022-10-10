package com.justcodeit.moyeo.study.application.user.dto;

import com.justcodeit.moyeo.study.persistence.JobType;
import lombok.Getter;

@Getter
public class EditUserReqDto {
    // TODO validation check 필요
    private String nickname;
    private String introduction;
    private JobType job1;
    private JobType job2;
    private JobType job3;
    private String portfolio;
    private String skills;
}
