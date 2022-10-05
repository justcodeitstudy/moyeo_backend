package com.justcodeit.moyeo.study.application.user.dto;

import lombok.Getter;

@Getter
public class EditUserReqDto {
    // TODO validation check 필요
    private String nickname;
    private String introduction;
    private String portfolio;
    private String skills;
}
