package com.justcodeit.moyeo.study.application.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.justcodeit.moyeo.study.application.user.exception.UserCannotFoundException;
import com.justcodeit.moyeo.study.model.type.Role;
import com.justcodeit.moyeo.study.persistence.User;
import com.justcodeit.moyeo.study.persistence.UserSkill;
import com.justcodeit.moyeo.study.persistence.repository.UserRepository;
import com.justcodeit.moyeo.study.persistence.repository.UserSkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles({ "local", "aws" })
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserSkillRepository userSkillRepository;

    User testUser;
    String testUserId;

    @BeforeEach
    void setUp() {
        testUserId = "testUserId";
        testUser = new User(testUserId, "e", "p", Role.USER, "dn", "pt", "di");
        userRepository.save(testUser);
        userSkillRepository.save(new UserSkill(testUser, 1L));
        userSkillRepository.save(new UserSkill(testUser, 2L));
    }

    @Test
    void userId_invalid_throw_exception() {
        var invalidId = "invalidId";
        assertThrows(UserCannotFoundException.class, () -> userService.accessProfile(invalidId));
    }

    // 현재 이것 원인 찾지 못함.
    @Test
    void userId_valid_return_dto() {
        var result = userService.accessProfile(testUserId);
        assertEquals(testUser.getEmail(), result.getEmail());
        assertEquals(testUser.getPicture(), result.getPicture());
        assertEquals(testUser.getNickname(), result.getNickname());
        assertEquals(testUser.getIntroduction(), result.getIntroduction());
        assertThat(result.getSkillIds(), containsInAnyOrder(1L,2L));
    }
}