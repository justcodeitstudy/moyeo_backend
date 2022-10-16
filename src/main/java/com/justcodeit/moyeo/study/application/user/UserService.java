package com.justcodeit.moyeo.study.application.user;

import com.justcodeit.moyeo.study.application.user.exception.UserCannotFoundException;
import com.justcodeit.moyeo.study.interfaces.dto.user.GetUserResDto;
import com.justcodeit.moyeo.study.persistence.User;
import com.justcodeit.moyeo.study.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserSkillService userSkillService;

    @Transactional(readOnly = true)
    public GetUserResDto accessProfile(String userId) {
        var user = getUser(userId);
        var skillIds = userSkillService.getSkillIds(user.getId());
        return new GetUserResDto(
            user.getEmail(),
            user.getPicture(),
            user.getNickname(),
            user.getIntroduction(),
            skillIds
        );
    }

    private User getUser(String userId) {
        return userRepository.findByUserId(userId)
            .orElseThrow(UserCannotFoundException::new);
    }
}
