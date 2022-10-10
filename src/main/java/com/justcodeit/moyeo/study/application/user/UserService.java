package com.justcodeit.moyeo.study.application.user;

import com.justcodeit.moyeo.study.application.skill.SkillService;
import com.justcodeit.moyeo.study.application.user.dto.EditUserReqDto;
import com.justcodeit.moyeo.study.application.user.dto.GetUserResDto;
import com.justcodeit.moyeo.study.persistence.User;
import com.justcodeit.moyeo.study.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SkillService skillService;

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
                .orElseThrow(() -> new RuntimeException());
                        //new UserCannotFoundException());
    }

    @Transactional
    public void editProfile(String userId, EditUserReqDto editUserReqDto){
        var user = getUser(userId);
        checkSkillIds(editUserReqDto.getSkillIds());
        user.editProfile(
            editUserReqDto.getNickname(),
            editUserReqDto.getIntroduction(),
            editUserReqDto.getSkillIds()
        );
    }

    private void checkSkillIds(List<Long> skillIds) {
        boolean isValid = skillIds.stream().allMatch(skillId -> skillService.isValidId(skillId));
        if (!isValid) {
            throw new RuntimeException();
                    //new SkillCannotFoundException();
        }
    }
}
