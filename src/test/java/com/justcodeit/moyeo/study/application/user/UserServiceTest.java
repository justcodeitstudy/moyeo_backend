package com.justcodeit.moyeo.study.application.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.justcodeit.moyeo.study.application.user.exception.UserCannotFoundException;
import com.justcodeit.moyeo.study.model.type.Role;
import com.justcodeit.moyeo.study.persistence.User;
import com.justcodeit.moyeo.study.persistence.UserSkill;
import com.justcodeit.moyeo.study.persistence.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserServiceTest {

    UserService userService;
    UserRepository userRepository;
    User testUser;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void userId_invalid_throw_exception() {
        var invalidId = "invalidId";
        when(userRepository.findByUserId(anyString())).thenThrow(UserCannotFoundException.class);
        assertThrows(UserCannotFoundException.class, () -> userService.accessProfile(invalidId));
    }

    @Test
    void userId_valid_return_dto() {
        when(userRepository.findByUserId(anyString())).thenAnswer((e) -> {
            testUser = new User((String)e.getArgument(0), "email", "picture", Role.USER, "display", "provider", "domesticId", "nickname");
            testUser.addUserSkill(new UserSkill(testUser, 1l));
            testUser.addUserSkill(new UserSkill(testUser, 2l));
            return Optional.of(testUser);
        });
        var userId = "testUserId";
        var result = userService.accessProfile(userId);
        assertEquals(testUser.getEmail(), result.getEmail());
        assertEquals(testUser.getPicture(), result.getPicture());
        assertEquals(testUser.getNickname(), result.getNickname());
        assertEquals(testUser.getIntroduction(), result.getIntroduction());
        assertThat(result.getSkillIds(), containsInAnyOrder(1L,2L));
    }
}