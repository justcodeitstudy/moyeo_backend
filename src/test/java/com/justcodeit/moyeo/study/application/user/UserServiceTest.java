package com.justcodeit.moyeo.study.application.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.justcodeit.moyeo.study.application.skill.exception.SkillCannotFoundException;
import com.justcodeit.moyeo.study.application.user.exception.UserCannotFoundException;
import com.justcodeit.moyeo.study.interfaces.dto.user.EditProfileReqDto;
import com.justcodeit.moyeo.study.model.type.Role;
import com.justcodeit.moyeo.study.persistence.User;
import com.justcodeit.moyeo.study.persistence.UserSkill;
import com.justcodeit.moyeo.study.persistence.repository.SkillRepository;
import com.justcodeit.moyeo.study.persistence.repository.UserRepository;
import com.justcodeit.moyeo.study.persistence.repository.UserSkillRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    UserSkillRepository userSkillRepository;
    @Mock
    SkillRepository skillRepository;

    User testUser;

    @Test
    void accessProfile_userId_invalid_throw_exception() {
        when(userRepository.findByUserId(anyString())).thenThrow(UserCannotFoundException.class);
        assertThrows(UserCannotFoundException.class, () -> userService.accessProfile("invalidId"));
    }

    @Test
    void accessProfile_userId_valid_return_dto() {
        setFindByUserResult();
        when(userSkillRepository.findSkillIdsByUserId(any())).thenReturn(List.of(1L, 2L));

        var result = userService.accessProfile("validUserId");

        assertEquals(testUser.getEmail(), result.getEmail());
        assertEquals(testUser.getPicture(), result.getPicture());
        assertEquals(testUser.getNickname(), result.getNickname());
        assertEquals(testUser.getIntroduction(), result.getIntroduction());
        assertThat(result.getSkillIds(), containsInAnyOrder(1L, 2L));
    }

    @Test
    void editProfile_userId_invalid_throw_exception() {
        when(userRepository.findByUserId(anyString())).thenThrow(UserCannotFoundException.class);
        assertThrows(UserCannotFoundException.class,
            () -> userService.editProfile("invalidId", new EditProfileReqDto()));
    }

    @Test
    void editProfile_skillIds_invalid_throw_exception() {
        setFindByUserResult();
        when(skillRepository.getCountByIds(any())).thenReturn(2L);
        var mockDto = mock(EditProfileReqDto.class);
        when(mockDto.getSkillIds()).thenReturn(List.of(1L, 2L, 3L));

        assertThrows(SkillCannotFoundException.class,
            () -> userService.editProfile("validUserId", mockDto));
    }

    private void setFindByUserResult() {
        when(userRepository.findByUserId(anyString())).thenAnswer((e) -> {
            testUser = new User((String) e.getArgument(0), "email", "picture", Role.USER, "display",
                "provider", "domesticId", "nickname");
            return Optional.of(testUser);
        });
    }

    @Test
    void editProfile_skillIds_valid_success() {
        setFindByUserResult();
        var skillIds = List.of(1L, 2L, 3L);
        var userSkills = skillIds.stream().map(skillId -> new UserSkill(1L, skillId)).collect(
            Collectors.toList());
        when(skillRepository.getCountByIds(any())).thenReturn(Long.valueOf(skillIds.size()));
        var mockDto = mock(EditProfileReqDto.class);
        when(mockDto.getSkillIds()).thenReturn(skillIds);
        doNothing().when(userSkillRepository).deleteByUserId(any());
        when(userSkillRepository.saveAll(any())).thenReturn(userSkills);

        userService.editProfile("validUserId", mockDto);
    }
}