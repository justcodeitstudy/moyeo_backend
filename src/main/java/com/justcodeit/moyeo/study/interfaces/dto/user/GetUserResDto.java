package com.justcodeit.moyeo.study.interfaces.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetUserResDto {
    private final String email;
    private final String picture;
    private final String nickname;
    private final String introduction;
    private final List<Long> skillIds;
}
