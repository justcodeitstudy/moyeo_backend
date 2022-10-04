package com.justcodeit.moyeo.study.application.user.dto;

import com.justcodeit.moyeo.study.persistence.JobType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetUserResDto {
    private final String userId;
    private final String email;
    private final String nickname;
    private final String introduction;
    private final JobType job1;
    private final JobType job2;
    private final JobType job3;
    private final String portfolio;
    private final String skills;
}
