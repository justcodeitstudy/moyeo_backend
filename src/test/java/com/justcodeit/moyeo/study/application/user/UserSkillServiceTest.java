package com.justcodeit.moyeo.study.application.user;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.when;

import com.justcodeit.moyeo.study.persistence.repository.UserSkillRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserSkillServiceTest {

    @InjectMocks
    UserSkillService userSkillService;
    @Mock
    UserSkillRepository userSkillRepository;

    @Test
    void getSkillIdsTest() {
        var userId = 10L;
        when(userSkillRepository.findSkillIdsByUserId(userId))
            .thenReturn(List.of(1L, 2L));
        assertThat(userSkillService.getSkillIds(userId), containsInAnyOrder(1L,2L));
    }
}