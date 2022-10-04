package com.justcodeit.moyeo.study.application.user.dto;

import com.justcodeit.moyeo.study.persistence.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetUserReqDto {
    private final UserRepository repo;
    private final String userId;
}
