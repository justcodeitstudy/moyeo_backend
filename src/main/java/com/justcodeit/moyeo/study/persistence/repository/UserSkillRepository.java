package com.justcodeit.moyeo.study.persistence.repository;

import com.justcodeit.moyeo.study.persistence.UserSkill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {

    @Query("SELECT us.skillId FROM UserSkill us WHERE us.userId = :userId ORDER BY us.id")
    List<Long> findSkillIdsByUserId(@Param("userId") Long userId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM UserSkill us WHERE us.userId = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
