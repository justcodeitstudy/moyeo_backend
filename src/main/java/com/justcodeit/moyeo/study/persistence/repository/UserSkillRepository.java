package com.justcodeit.moyeo.study.persistence.repository;

import com.justcodeit.moyeo.study.persistence.UserSkill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {

    List<UserSkill> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}
