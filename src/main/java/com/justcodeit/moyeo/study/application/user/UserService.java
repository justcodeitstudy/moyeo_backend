package com.justcodeit.moyeo.study.application.user;

import com.justcodeit.moyeo.study.application.user.exception.UserCannotFoundException;
import com.justcodeit.moyeo.study.interfaces.dto.user.GetUserResDto;
import com.justcodeit.moyeo.study.persistence.User;
import com.justcodeit.moyeo.study.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public GetUserResDto accessProfile(String userId) {
        var user = getUser(userId);
        return new GetUserResDto(
            user.getEmail(),
            user.getPicture(),
            user.getNickname(),
            user.getIntroduction(),
            user.getSkillIds()
        );
    }

    private User getUser(String userId) {
        return userRepository.findByUserId(userId)
            .orElseThrow(UserCannotFoundException::new);
    }
}
