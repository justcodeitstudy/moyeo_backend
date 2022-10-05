package com.justcodeit.moyeo.study.application.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetUserResDto {
    private final String email;
    private final String nickname;
    private final String introduction;
    private final String portfolio;
    private final String skills;
}
