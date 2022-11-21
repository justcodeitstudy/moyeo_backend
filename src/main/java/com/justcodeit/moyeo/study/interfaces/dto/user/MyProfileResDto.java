package com.justcodeit.moyeo.study.interfaces.dto.user;

import java.util.List;

import com.justcodeit.moyeo.study.model.skill.SkillDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MyProfileResDto {

    private final String email;
    private final String picture;
    private final String nickname;
    private final String introduction;
    private final List<SkillDto> skillIds;

    public static MyProfileResDto fromProfileInfo(ProfileInfo profileInfo) {
        return new MyProfileResDto(
            profileInfo.getEmail(),
            profileInfo.getPicture(),
            profileInfo.getNickname(),
            profileInfo.getIntroduction(),
            profileInfo.getSkillIds()
        );
    }
}
