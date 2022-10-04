package com.justcodeit.moyeo.study.application.user;

import com.justcodeit.moyeo.study.application.user.dto.EditUserReqDto;
import com.justcodeit.moyeo.study.application.user.dto.GetUserResDto;
import com.justcodeit.moyeo.study.application.user.exception.UserCannotFoundException;
import com.justcodeit.moyeo.study.persistence.User;
import com.justcodeit.moyeo.study.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public GetUserResDto accessProfile(String userId) {
        var user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserCannotFoundException());
        return getUser(user);
    }

    public GetUserResDto getUser(User user) {
        return new GetUserResDto(
                user.getUserId(),
                user.getEmail(),
                user.getNickname(),
                user.getIntroduction(),
                user.getJob1(),
                user.getJob2(),
                user.getJob3(),
                user.getPortfolio(),
                user.getSkills()
        );
    }

    @Transactional
    public GetUserResDto editProfile(String userId, EditUserReqDto editUserReqDto){
        var user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserCannotFoundException());
        user.editProfile(
                editUserReqDto.getNickname(),
                editUserReqDto.getIntroduction(),
                editUserReqDto.getJob1(),
                editUserReqDto.getJob2(),
                editUserReqDto.getJob3(),
                editUserReqDto.getPortfolio(),
                editUserReqDto.getSkills()
        );
        return getUser(userRepository.save(user));
    }

}
