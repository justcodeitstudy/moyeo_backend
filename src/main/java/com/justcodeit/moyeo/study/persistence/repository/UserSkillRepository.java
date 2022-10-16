package com.justcodeit.moyeo.study.persistence.repository;

import com.justcodeit.moyeo.study.persistence.UserSkill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {
    @Query("select us.skillId from UserSkill us where us.userId = :userId")
    List<Long> findSkillIdsByUserId(@Param("userId") Long userId);
}
