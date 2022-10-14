package com.justcodeit.moyeo.study.application.user;

import com.justcodeit.moyeo.study.persistence.repository.UserSkillRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserSkillService {

    private final UserSkillRepository userSkillRepository;

    List<Long> getSkillIds(Long userId) {
        return userSkillRepository.findSkillIdsByUserId(userId);
    }
}