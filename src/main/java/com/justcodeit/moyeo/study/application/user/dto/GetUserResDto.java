package com.justcodeit.moyeo.study.application.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetUserResDto {
    private final String email;
    private final String nickname;
    private final String introduction;
    private final String portfolio;
    private final String skills;
}
