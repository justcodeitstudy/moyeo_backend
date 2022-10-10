package com.justcodeit.moyeo.study.application.user;

import com.justcodeit.moyeo.study.application.user.exception.UserCannotFoundException;
import com.justcodeit.moyeo.study.interfaces.dto.user.GetUserResDto;
import com.justcodeit.moyeo.study.persistence.User;
import com.justcodeit.moyeo.study.persistence.repository.UserRepository;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public GetUserResDto accessProfile(String userId) {
        var user = getUser(userId);
        // TODO userToken 병합 후 실제 함수로 작업
        // TODO skillIds 는 UserSkill 테이블의 skill 아이디를 Long List 로 전달한다.
        return new GetUserResDto(
            "user.getEmail()",
            "user.getPicture()",
            "user.getNickname()",
            "user.getIntroduction()",
            new ArrayList<Long>()
        );
    }

    private User getUser(String userId) {
        return userRepository.findByUserId(userId)
            .orElseThrow(() -> new UserCannotFoundException());
    }
}
