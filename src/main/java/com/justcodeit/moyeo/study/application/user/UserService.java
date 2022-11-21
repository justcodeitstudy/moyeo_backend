package com.justcodeit.moyeo.study.application.user;

import com.justcodeit.moyeo.study.application.skill.exception.SkillCannotFoundException;
import com.justcodeit.moyeo.study.application.user.exception.UserCannotFoundException;
import com.justcodeit.moyeo.study.interfaces.dto.user.EditProfileReqDto;
import com.justcodeit.moyeo.study.interfaces.dto.user.ProfileInfo;
import com.justcodeit.moyeo.study.interfaces.mapper.SkillMapper;
import com.justcodeit.moyeo.study.model.skill.SkillDto;
import com.justcodeit.moyeo.study.persistence.User;
import com.justcodeit.moyeo.study.persistence.UserSkill;
import com.justcodeit.moyeo.study.persistence.repository.SkillRepository;
import com.justcodeit.moyeo.study.persistence.repository.UserRepository;
import com.justcodeit.moyeo.study.persistence.repository.UserSkillRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserSkillRepository userSkillRepository;
    private final SkillRepository skillRepository;

    @Transactional(readOnly = true)
    public ProfileInfo accessProfile(String userId) {
        var user = getUser(userId);
        var skillIds = getSkillList(user.getId());
        return new ProfileInfo(
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

    private List<SkillDto> getSkillList(Long userId) {
        return userSkillRepository.findByUserId(userId)
                .stream().map(
                        userSkill -> SkillMapper.SKILL_INSTANCE.entityToSkillDto(
                                skillRepository.findById(userSkill.getSkillId())
                                .orElseThrow(SkillCannotFoundException::new)))
                .collect(Collectors.toList());
    }
    private List<Long> getSkillIds(Long userId) {
        return userSkillRepository.findByUserId(userId)
                .stream().map(userSkill -> userSkill.getSkillId())
                .collect(Collectors.toList());
    }

    @Transactional
    public void editProfile(String userId, EditProfileReqDto editProfileReqDto) {
        var user = getUser(userId);
        checkSkillIds(editProfileReqDto.getSkillIds());
        user.editProfile(
            editProfileReqDto.getNickname(),
            editProfileReqDto.getIntroduction()
        );
        editUserSkill(editProfileReqDto, user.getId());
    }

    private void checkSkillIds(List<Long> skillIds) {
        if (skillIds.size() != skillRepository.countByIdIn(skillIds)) {
            throw new SkillCannotFoundException();
        }
    }

    private void editUserSkill(EditProfileReqDto editProfileReqDto, Long userId) {
        userSkillRepository.deleteByUserId(userId);
        userSkillRepository.saveAll(
            editProfileReqDto.getSkillIds().stream().map(
                skillId -> new UserSkill(userId, skillId)
            ).collect(Collectors.toList())
        );
    }
}
