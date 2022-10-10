package com.justcodeit.moyeo.study.persistence.repository;

import com.justcodeit.moyeo.study.application.user.exception.UserCannotFoundException;
import com.justcodeit.moyeo.study.model.type.Role;
import com.justcodeit.moyeo.study.persistence.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        var user = new User("userId", "e", "p", Role.USER, "dn", "pt", "di", "n");
        userRepository.save(user);
    }

    @Test
    void editProfile() {
        var userId = "userId";
        User user;
        user = userRepository.findByUserId(userId).orElseThrow(() -> new UserCannotFoundException());
        assertEquals(0, user.getSkillIds().size());

        user.editProfile("n2", "i2", List.of(1L, 2L));
        userRepository.saveAndFlush(user);

        user = userRepository.findByUserId(userId).orElseThrow(() -> new UserCannotFoundException());
        assertThat(user.getSkillIds()).containsExactly(1L, 2L);
        assertEquals(user.getNickname(), "n2");
        assertEquals(user.getIntroduction(), "i2");

        user.editProfile("n3", "i3", List.of(3L, 4L, 5L));
        userRepository.saveAndFlush(user);

        user = userRepository.findByUserId(userId).orElseThrow(() -> new UserCannotFoundException());
        assertThat(user.getSkillIds()).containsExactly(3L, 4L, 5L);
        assertEquals(user.getNickname(), "n3");
        assertEquals(user.getIntroduction(), "i3");
    }
}