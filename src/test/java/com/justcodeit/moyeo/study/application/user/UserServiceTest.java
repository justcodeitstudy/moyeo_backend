package com.justcodeit.moyeo.study.application.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.justcodeit.moyeo.study.application.user.exception.UserCannotFoundException;
import com.justcodeit.moyeo.study.model.type.Role;
import com.justcodeit.moyeo.study.persistence.User;
import com.justcodeit.moyeo.study.persistence.repository.UserRepository;
import java.util.List;
import java.util.Optional;
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
    UserSkillService userSkillService;
    User testUser;

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
            return Optional.of(testUser);
        });
        when(userSkillService.getSkillIds(any())).thenReturn(List.of(1L,2L));
        var userId = "testUserId";
        var result = userService.accessProfile(userId);
        assertEquals(testUser.getEmail(), result.getEmail());
        assertEquals(testUser.getPicture(), result.getPicture());
        assertEquals(testUser.getNickname(), result.getNickname());
        assertEquals(testUser.getIntroduction(), result.getIntroduction());
        assertThat(result.getSkillIds(), containsInAnyOrder(1L,2L));
    }
}